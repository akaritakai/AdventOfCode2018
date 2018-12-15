package net.akaritakai.aoc2018;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


public class Problem01 extends AbstractProblem {
  @Override
  public int getDay() {
    return 1;
  }

  @Override
  public String solvePart1() {
    final var sum = getInput().stream().mapToLong(i -> i).sum();
    return String.valueOf(sum);
  }

  @Override
  public String solvePart2() {
    final var inputs = getInput();
    final var sums = new HashSet<>();
    sums.add(0L);
    var sum = 0L;
    while (true) {
      for (final var input : inputs) {
        sum += input;
        if (!sums.add(sum)) {
          return String.valueOf(sum);
        }
      }
    }
  }

  private List<Integer> getInput() {
    return getPuzzleInput().lines().map(Integer::parseInt).collect(Collectors.toList());
  }
}
