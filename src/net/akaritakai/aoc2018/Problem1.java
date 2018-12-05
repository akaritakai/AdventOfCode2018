package net.akaritakai.aoc2018;

import java.util.HashSet;

public class Problem1 extends AbstractProblem {
    @Override
    public int getDay() {
        return 1;
    }

    @Override
    public String solvePart1() {
        final var sum = getPuzzleInputNumbers().stream().mapToLong(i -> i).sum();
        return String.valueOf(sum);
    }

    @Override
    public String solvePart2() {
        final var inputs = getPuzzleInputNumbers();
        final var sums = new HashSet<>();
        long sum = 0;
        while (true) {
            for (final var input : inputs) {
                sum += input;
                if (!sums.add(sum)) {
                    return String.valueOf(sum);
                }
            }
        }
    }
}
