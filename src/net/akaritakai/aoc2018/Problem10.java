package net.akaritakai.aoc2018;

import org.intellij.lang.annotations.RegExp;

import java.awt.*;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
public class Problem10 extends AbstractProblem {
    @Override
    public int getDay() {
        return 10;
    }

    @Override
    public String solvePart1() {
        var inputs = getInput();

        // Get the moment with the area is the smallest
        var lastSize = getLightArea(inputs);
        while (true) {
            var currentSize = getLightArea(inputs);
            if (currentSize.compareTo(lastSize) > 0) {
                break;
            }
            lastSize = currentSize;
            inputs.forEach(Lights::move);
        }
        inputs.forEach(Lights::undoMove);

        // Calculate the bounds of the area
        final var minHeight = inputs.stream().mapToInt(pm -> pm.position.y).min().orElseThrow();
        final var maxHeight = inputs.stream().mapToInt(pm -> pm.position.y).max().orElseThrow();
        final var minWidth =  inputs.stream().mapToInt(pm -> pm.position.x).min().orElseThrow();
        final var maxWidth =  inputs.stream().mapToInt(pm -> pm.position.x).max().orElseThrow();

        // Print the area out to reveal the message
        final var area = new StringBuilder();
        for (var height = minHeight - 1; height <= maxHeight + 1; height++) {
            for (var width = minWidth - 1; width <= maxWidth + 1; width++) {
                final var x = width;
                final var y = height;
                final boolean hasPoint = inputs.stream().anyMatch(pm -> pm.position.x == x && pm.position.y == y);
                if (hasPoint) {
                    area.append("#");
                } else {
                    area.append(".");
                }
            }
            area.append("\n");
        }
        return "\n" + area.toString().trim();
    }

    @Override
    public String solvePart2() {
        final var inputs = getInput();

        // Get the moment with the area is the smallest
        var step = 0;
        var lastSize = getLightArea(inputs);
        while (true) {
            var currentSize = getLightArea(inputs);
            if (currentSize.compareTo(lastSize) > 0) {
                step--;
                break;
            }
            lastSize = currentSize;
            inputs.forEach(Lights::move);
            step++;
        }

        // Print out how many steps that took
        return String.valueOf(step);
    }

    private BigInteger getLightArea(final List<Lights> lights) {
        final var minHeight = lights.stream().mapToInt(pm -> pm.position.y).min().orElseThrow();
        final var maxHeight = lights.stream().mapToInt(pm -> pm.position.y).max().orElseThrow();
        final var minWidth =  lights.stream().mapToInt(pm -> pm.position.x).min().orElseThrow();
        final var maxWidth =  lights.stream().mapToInt(pm -> pm.position.x).max().orElseThrow();

        final var height = BigInteger.valueOf(maxHeight).subtract(BigInteger.valueOf(minHeight)).abs();
        final var width = BigInteger.valueOf(maxWidth).subtract(BigInteger.valueOf(minWidth)).abs();

        return height.multiply(width);
    }

    private List<Lights> getInput() {
        return getPuzzleInputLines()
                .stream()
                .map(line -> {
                    @RegExp final var regex = "^position=<\\s*(-?\\d+),\\s*(-?\\d+)>\\s*velocity=<\\s*(-?\\d+),\\s*(-?\\d+)>$";
                    final var x = Integer.parseInt(line.replaceAll(regex, "$1"));
                    final var y = Integer.parseInt(line.replaceAll(regex, "$2"));
                    final var dx = Integer.parseInt(line.replaceAll(regex, "$3"));
                    final var dy = Integer.parseInt(line.replaceAll(regex, "$4"));
                    return new Lights(new Point(x, y), dx, dy);
                })
                .collect(Collectors.toList());
    }

    static class Lights {
        Point position;
        final int dx;
        final int dy;

        Lights(Point position, int dx, int dy) {
            this.position = position;
            this.dx = dx;
            this.dy = dy;
        }

        void move() {
            position = new Point(position.x + dx, position.y + dy);
        }

        void undoMove() {
            position = new Point(position.x - dx, position.y - dy);
        }
    }
}
