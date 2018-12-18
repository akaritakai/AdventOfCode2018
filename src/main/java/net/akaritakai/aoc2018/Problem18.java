package net.akaritakai.aoc2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;


@SuppressWarnings("Duplicates")
public class Problem18 extends AbstractProblem {
  @Override
  public int getDay() {
    return 18;
  }

  @Override
  public String solvePart1() {
    processInput();

    for (var i = 0; i < 10; i++) {
      iterate();
    }

    return String.valueOf(getResourceValue());
  }

  @Override
  public String solvePart2() {
    /*
     * This is another Game of Life-like problem (like Problem 12). It seems everyone's inputs converge to a
     * repeating set of inputs within ~1000 evolutions.
     */

    processInput();

    final var previous = new ArrayList<>();
    final var seen = new HashMap<>();

    for (var i = 0; i < 1_000_000_000; i++) {
      final var acres = serialize();
      previous.add(acres);
      if (seen.containsKey(acres)) {
        break;
      }
      seen.put(acres, getResourceValue());
      iterate();
    }

    // If there truly were no repeats, then just output the getResourceValue we found at this point.
    if (seen.size() == 1_000_000_000) {
      return String.valueOf(getResourceValue());
    }

    // Find the start of our repeats and its period
    var start = 0;
    var period = 0;
    for (var i = previous.size() - 2; i >= 0; i--) {
      if (Objects.equals(previous.get(previous.size() - 1), previous.get(i))) {
        start = i;
        period = (previous.size() - 1) - i;
        break;
      }
    }

    final var offset = (1_000_000_000 - start) % period;
    final var value = seen.get(previous.get(start + offset));

    return String.valueOf(value);
  }

  /**
   * Serializes the grid into a String format.
   */
  private String serialize() {
    final var sb = new StringBuilder();
    for (char[] acre : acres) {
        sb.append(acre);
    }
    return sb.toString();
  }

  /**
   * Gets the resource value of the lumber collection area.
   */
  private long getResourceValue() {
    int trees = 0;
    int lumberyards = 0;
    for (char[] acre : acres) {
      for (int i = 0; i < acres[0].length; i++) {
        switch (acre[i]) {
          case '|': trees++; break;
          case '#': lumberyards++; break;
        }
      }
    }
    return trees * lumberyards;
  }

  /**
   * Evolves the lumber collection area based on our rules.
   */
  private void iterate() {
    final var oldAcres = Arrays.stream(acres).map(char[]::clone).toArray(char[][]::new);
    for (var x = 0; x < acres.length; x++) {
      for (var y = 0; y < acres[0].length; y++) {
        final var treeCount = getNeighboringTreeCount(oldAcres, x, y);
        final var lumberyardCount = getNeighboringLumberyardCount(oldAcres, x, y);
        switch (acres[x][y]) {
          case '.':
            if (treeCount >= 3) acres[x][y] = '|';
            break;
          case '|':
            if (lumberyardCount >= 3) acres[x][y] = '#';
            break;
          case '#':
            if (lumberyardCount < 1 || treeCount < 1) acres[x][y] = '.';
            break;
        }
      }
    }
  }

  private int getNeighboringTreeCount(@NotNull final char[][] acres, final int x, final int y) {
    int count = 0;
    for (var i = x - 1; i <= x + 1; i++) {
      for (var j = y - 1; j <= y + 1; j++) {
        if (i == x && j == y) {
          continue; // skip ourself
        }
        if (i < 0 || i >= acres.length || j < 0 || j >= acres[0].length) {
          continue; // out of bounds
        }
        if (acres[i][j] == '|') {
          count++;
        }
      }
    }
    return count;
  }

  private int getNeighboringLumberyardCount(@NotNull final char[][] acres, final int x, final int y) {
    int count = 0;
    for (var i = x - 1; i <= x + 1; i++) {
      for (var j = y - 1; j <= y + 1; j++) {
        if (i == x && j == y) {
          continue; // skip ourself
        }
        if (i < 0 || i >= acres.length || j < 0 || j >= acres[0].length) {
          continue; // out of bounds
        }
        if (acres[i][j] == '#') {
          count++;
        }
      }
    }
    return count;
  }

  private char[][] acres;

  private void processInput() {
    final var lines = getPuzzleInput().lines()
       .map(String::trim)
       .filter(line -> !line.isEmpty())
       .collect(Collectors.toList());

    final var width = lines.stream().mapToInt(String::length).max().orElseThrow();
    final var height = lines.size();

    acres = new char[width][height];

    for (var y = 0; y < height; y++) {
      for (var x = 0; x < width; x++) {
        acres[x][y] = lines.get(y).charAt(x);
      }
    }
  }
}
