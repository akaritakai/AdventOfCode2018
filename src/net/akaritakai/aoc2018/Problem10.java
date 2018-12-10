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

        // Get the moment when the area is the smallest
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
        final var area = getLightDimensions(inputs);

        // Print the area out to reveal the message
        final var sb = new StringBuilder();
        for (var height = area.y - 1; height <= area.y + area.height + 1; height++) {
            for (var width = area.x - 1; width <= area.x + area.width + 1; width++) {
                final var x = width;
                final var y = height;
                final boolean hasPoint = inputs.stream().anyMatch(pm -> pm.position.x == x && pm.position.y == y);
                if (hasPoint) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return "\n" + sb.toString().trim();
    }

    @Override
    public String solvePart2() {
        final var inputs = getInput();

        // Get the moment when the area is the smallest
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

    private Rectangle getLightDimensions(final List<Lights> lights) {
        final var minHeight = lights.stream().mapToInt(pm -> pm.position.y).min().orElseThrow();
        final var maxHeight = lights.stream().mapToInt(pm -> pm.position.y).max().orElseThrow();
        final var minWidth =  lights.stream().mapToInt(pm -> pm.position.x).min().orElseThrow();
        final var maxWidth =  lights.stream().mapToInt(pm -> pm.position.x).max().orElseThrow();

        return new Rectangle(minWidth, minHeight, maxWidth - minWidth, maxHeight - minHeight);
    }

    private BigInteger getLightArea(final List<Lights> lights) {
        var area = getLightDimensions(lights);

        final var height = BigInteger.valueOf(area.y).subtract(BigInteger.valueOf(area.y + area.height)).abs();
        final var width = BigInteger.valueOf(area.x).subtract(BigInteger.valueOf(area.x + area.width)).abs();

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
