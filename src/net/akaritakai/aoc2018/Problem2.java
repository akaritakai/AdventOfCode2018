package net.akaritakai.aoc2018;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Problem2 extends AbstractProblem {
    @Override
    protected int getDay() {
        return 2;
    }

    @Override
    public String solvePart1() {
        long twoCount = 0;
        long threeCount = 0;

        for (String input : getInputs()) {
            if (characterAppearsNTimes(input, 2)) {
                twoCount++;
            }
            if (characterAppearsNTimes(input, 3)) {
                threeCount++;
            }
        }

        long checksum = twoCount * threeCount;
        return String.valueOf(checksum);
    }

    @Override
    public String solvePart2() {
        for (String input1 : getInputs()) {
            for (String input2 : getInputs()) {
                assert(input1.length() == input2.length());
                String common = getCommonCharacters(input1, input2);
                if (common.length() == input1.length() - 1) {
                    return common;
                }
            }
        }
        return null;
    }

    private Map<String, Long> characterFrequencyMap(@NotNull final String s) {
        return s.chars()
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private boolean characterAppearsNTimes(@NotNull final String s, final int n) {
        return characterFrequencyMap(s)
                .values()
                .stream()
                .anyMatch(count -> count == n);
    }

    private String getCommonCharacters(@NotNull final String s1, @NotNull final String s2) {
        assert(s1.length() == s2.length());
        final StringBuilder similar = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if (c1 == c2) {
                similar.append(c1);
            }
        }
        return similar.toString();
    }

    private List<String> getInputs() {
        return getPuzzleInput()
                .lines()
                .collect(Collectors.toList());
    }
}
