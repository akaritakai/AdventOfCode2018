package net.akaritakai.aoc2018;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Problem1 extends AbstractProblem {
    @Override
    protected int getDay() {
        return 1;
    }

    @Override
    public String solvePart1() {
        final long sum = getInputNumbers().stream().mapToLong(i -> i).sum();
        return String.valueOf(sum);
    }

    @Override
    public String solvePart2() {
        final List<Long> inputs = getInputNumbers();
        final Set<Long> sums = new HashSet<>();
        long sum = 0;
        while (true) {
            for (long input : inputs) {
                sum += input;
                if (!sums.add(sum)) {
                    return String.valueOf(sum);
                }
            }
        }
    }

    private List<Long> getInputNumbers() {
        return getPuzzleInput()
                .lines()
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
    }
}
