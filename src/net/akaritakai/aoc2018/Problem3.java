package net.akaritakai.aoc2018;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Problem3 extends AbstractProblem {
    @Override
    public int getDay() {
        return 3;
    }

    @Override
    public String solvePart1() {
        final var count = getClaims().stream()
                .flatMap(claim -> claim.getPoints().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values().stream()
                .filter(v -> v > 1)
                .count();
        return String.valueOf(count);
    }

    @Override
    public String solvePart2() {
        return getClaims().parallelStream()
                .filter(claim1 -> getClaims().parallelStream()
                        .filter(claim2 -> claim1.id != claim2.id)
                        .noneMatch(claim2 -> claim1.rectangle.intersects(claim2.rectangle)))
                .findFirst()
                .map(claim -> String.valueOf(claim.id))
                .orElse(null);
    }

    private List<Claim> getClaims() {
        return getPuzzleInput()
                .lines()
                .map(Claim::fromString)
                .collect(Collectors.toList());
    }

    static class Claim {
        final int id;
        final Rectangle rectangle;

        Claim(final int id, @NotNull final Rectangle rectangle) {
            this.id = id;
            this.rectangle = rectangle;
        }

        List<Point> getPoints() {
            final List<Point> points = new ArrayList<>();
            for (var x = 0; x < rectangle.width; x++) {
                for (var y = 0; y < rectangle.height; y++) {
                    points.add(new Point(rectangle.x + x, rectangle.y + y));
                }
            }
            return points;
        }

        static Claim fromString(@NotNull final String s) {
            @RegExp final String regex = "^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)$";
            final var id = Integer.parseInt(s.replaceAll(regex, "$1"));
            final var x = Integer.parseInt(s.replaceAll(regex, "$2"));
            final var y = Integer.parseInt(s.replaceAll(regex, "$3"));
            final var width = Integer.parseInt(s.replaceAll(regex, "$4"));
            final var height = Integer.parseInt(s.replaceAll(regex, "$5"));
            return new Claim(id, new Rectangle(x, y, width, height));
        }

    }
}
