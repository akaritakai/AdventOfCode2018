package net.akaritakai.aoc2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    final var trees = Arrays.stream(acres).flatMap(Arrays::stream).filter(c -> c == '|').count();
    final var lumberyards = Arrays.stream(acres).flatMap(Arrays::stream).filter(c -> c == '#').count();

    return String.valueOf(trees * lumberyards);
  }

  @Override
  public String solvePart2() {
    processInput();

    final var previous = new ArrayList<>();
    final var seen = new HashMap<>();

    for (var i = 0; i < 1_000_000_000; i++) {
      final var acreString = Arrays.stream(acres)
          .flatMap(Arrays::stream)
          .map(String::valueOf)
          .collect(Collectors.joining());
      previous.add(acreString);
      if (seen.containsKey(acreString)) {
        break;
      }
      seen.put(acreString, score());
      iterate();
    }

    var start = 0;
    var period = 0;
    final var lastAcre = previous.get(previous.size() - 1);
    for (var i = previous.size() - 2; i >= 0; i--) {
      final var acre = previous.get(i);
      if (Objects.equals(lastAcre, acre)) {
        start = i;
        period = (previous.size() - 1) - (i);
        break;
      }
    }

    final var offset = (1_000_000_000 - start) % period;
    final var score = seen.get(previous.get(start + offset));

    return String.valueOf(score);
  }

  private long score() {
    final var trees = Arrays.stream(acres).flatMap(Arrays::stream).filter(c -> c == '|').count();
    final var lumberyards = Arrays.stream(acres).flatMap(Arrays::stream).filter(c -> c == '#').count();
    return trees * lumberyards;
  }

  private void iterate() {
    final var oldAcres = Arrays.stream(acres).map(Character[]::clone).toArray(Character[][]::new);
    for (var x = 0; x < acres.length; x++) {
      for (var y = 0; y < acres[0].length; y++) {
        final var neighborhood = getNeighborhood(oldAcres, x, y);
        final var treeCount = neighborhood.stream().filter(a -> a == '|').count();
        final var lumberyardCount = neighborhood.stream().filter(a -> a == '#').count();
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

  private List<Character> getNeighborhood(@NotNull final Character[][] acres, final int x, final int y) {
    final var neighborhood = new ArrayList<Character>();
    for (var i = x - 1; i <= x + 1; i++) {
      for (var j = y - 1; j <= y + 1; j++) {
        if (i == x && j == y) {
          continue; // skip ourself
        }
        if (i < 0 || i >= acres.length || j < 0 || j >= acres[0].length) {
          continue; // out of bounds
        }
        neighborhood.add(acres[i][j]);
      }
    }
    return neighborhood;
  }

  private Character[][] acres;

  private void processInput() {
    final var lines = getPuzzleInput().lines()
       .map(String::trim)
       .filter(line -> !line.isEmpty())
       .collect(Collectors.toList());

    final var width = lines.stream().mapToInt(String::length).max().orElseThrow();
    final var height = lines.size();

    acres = new Character[width][height];

    for (var y = 0; y < height; y++) {
      for (var x = 0; x < width; x++) {
        char c = lines.get(y).charAt(x);
        acres[x][y] = c;
      }
    }
  }
}
