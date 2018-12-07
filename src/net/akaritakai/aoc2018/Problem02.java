package net.akaritakai.aoc2018;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Problem02 extends AbstractProblem {
    @Override
    public int getDay() {
        return 2;
    }

    @Override
    public String solvePart1() {
        var twoCount = 0;
        var threeCount = 0;

        for (final var input : getPuzzleInputLines()) {
            if (characterAppearsNTimes(input, 2)) {
                twoCount++;
            }
            if (characterAppearsNTimes(input, 3)) {
                threeCount++;
            }
        }

        final var checksum = twoCount * threeCount;
        return String.valueOf(checksum);
    }

    @Override
    public String solvePart2() {
        for (final var input1 : getPuzzleInputLines()) {
            for (final var input2 : getPuzzleInputLines()) {
                assert(input1.length() == input2.length());
                final var common = getCommonCharacters(input1, input2);
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
        final var similar = new StringBuilder();
        for (var i = 0; i < s1.length(); i++) {
            final var c1 = s1.charAt(i);
            final var c2 = s2.charAt(i);
            if (c1 == c2) {
                similar.append(c1);
            }
        }
        return similar.toString();
    }
}
