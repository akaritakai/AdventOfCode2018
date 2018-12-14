package net.akaritakai.aoc2018;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import org.intellij.lang.annotations.RegExp;


public class Problem12 extends AbstractProblem {
  @Override
  public int getDay() {
    return 12;
  }
  @Override
  public String solvePart1() {
    cells = getInitialState();
    rules = getRules();
    position = 0;
    for (var i = 1; i <= 20; i++) {
      iterate();
    }
    return String.valueOf(sum());
  }

  @Override
  public String solvePart2() {
    /*
     * This part 2 solution won't work with any arbitrary input, but should work for any Advent of Code input.
     *
     * Basically since this is a problem of elementary cellular automata, it is possible to have an encoding that will
     * emulate some Turing complete problem. As such, for an arbitrary input, there is no shortcut.
     *
     * However, the observation on the reddit threads is that the rules that Advent of Code give for any provided input
     * converge to stable single-glider states.
     *
     * As such, our solution is to observe this convergence and then extrapolate out what the position will be at the
     * target generation.
     */

    cells = getInitialState();
    rules = getRules();
    position = 0;

    long i = 1;
    var lastCells = cells;
    var lastPosition = position;
    for (; i <= 50_000_000_000L; i++) {
      iterate();
      if (lastCells.equals(cells)) {
        // We found our convergence
        break;
      }
      lastCells = cells;
      lastPosition = position;
    }

    // Find our current data point
    final var currentGeneration = i;
    final var currentSum = sum();

    // Find our previous data point
    final var previousGeneration = i - 1;
    cells = lastCells;
    position = lastPosition;
    final var previousSum = sum();

    // Extrapolate what the sum would be at 50 billion generations
    return extrapolate(previousGeneration, previousSum, currentGeneration, currentSum, 50_000_000_000L).toString();
  }

  private BigInteger extrapolate(final long oldGen, final long oldSum, final long newGen, final long newSum,
      final long targetGen) {
    assert(newGen - oldGen == 1);
    final var slope = BigInteger.valueOf(newSum).subtract(BigInteger.valueOf(oldSum));
    return slope.multiply(BigInteger.valueOf(targetGen))
        .add(BigInteger.valueOf(oldSum).subtract(slope.multiply(BigInteger.valueOf(oldGen))));
  }

  /**
   * Our current set of cells
   */
  private String cells;

  /**
   * Our current list of rules
   */
  private List<Rule> rules;

  /**
   * The position of the first cell (cell 0) relative to our current in-memory set of cells
   */
  private long position = 0;

  /**
   * Find the next generation of cells given their current state and the list of rules
   */
  private void iterate() {
    final var sb = new StringBuilder();

    // Append an inactive neighborhood on either end of our current cells
    final var oldCells = "....." + cells + ".....";
    position += 5;

    // Calculate the new state of each cell
    for (int i = 0; i < oldCells.length(); i++) {
      final var neighborhood = getNeighborhood(oldCells, i);
      final var newCell = rules.stream()
          .filter(rule -> rule.match.equals(neighborhood))
          .map(rule -> rule.result)
          .findAny()
          .orElseThrow();
      sb.append(newCell);
    }
    var newCells = sb.toString();

    // Strip off inactive neighborhoods
    while (newCells.startsWith(".")) {
      position -= 1;
      newCells = newCells.substring(1);
    }
    while (newCells.endsWith(".")) {
      newCells = newCells.substring(0, newCells.length() - 1);
    }

    cells = newCells;
  }


  /**
   * Get the sum of positions of cells marked active
   */
  private long sum() {
    var sum = 0;
    for (var i = 0; i < cells.length(); i++) {
      var pot = i - position;
      if (cells.charAt(i) == '#') {
        sum += pot;
      }
    }
    return sum;
  }

  /**
   * Get the neighborhood for the cell at index i within a set of cells
   */
  private String getNeighborhood(final String cells, final int i) {
    if (i - 2 < 0) {
      final var result = new StringBuilder(cells.substring(0, i + 3));
      while (result.length() < 5) {
        result.insert(0, ".");
      }
      return result.toString();
    }
    if (i + 3 >= cells.length()) {
      final var result = new StringBuilder(cells.substring(i - 2));
      while (result.length() < 5) {
        result.append(".");
      }
      return result.toString();
    }
    return cells.substring(i - 2, i + 3);
  }

  private String getInitialState() {
    return getPuzzleInput()
        .lines()
        .filter(line -> line.startsWith("initial state: "))
        .map(line -> line.replace("initial state: ", "").trim())
        .findAny()
        .orElseThrow();
  }

  private List<Rule> getRules() {
    return getPuzzleInput()
        .lines()
        .filter(line -> line.contains("=>"))
        .map(line -> {
          @RegExp final var regex = "^(\\S+?)\\s+=>\\s+(\\S+)$";
          final var match = line.replaceAll(regex, "$1");
          final var result = line.replaceAll(regex, "$2");
          return new Rule(match, result);
        })
        .collect(Collectors.toList());
  }

  private static class Rule {
    final String match;
    final String result;

    Rule(String match, String result) {
      this.match = match;
      this.result = result;
    }
  }
}
