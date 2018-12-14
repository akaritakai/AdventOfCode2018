package net.akaritakai.aoc2018;

import java.awt.*;

@SuppressWarnings("Duplicates")
public class Problem11 extends AbstractProblem {
    @Override
    public int getDay() {
        return 11;
    }

    @Override
    public String solvePart1() {
        final var serialNumber = Integer.parseInt(getPuzzleInput().trim());
        final var cells = getFuelCellPartialSums(serialNumber);

        var maxSum = 0;
        var point = new Point(0, 0);
        var size = 3;
        for (var x = 1; x <= 300 - size; x++) {
            for (var y = 1; y <= 300 - size; y++) {
                var sum = cells[x + size][y + size] - cells[x + size][y] - cells[x][y + size] + cells[x][y];
                if (sum >= maxSum) {
                    maxSum = sum;
                    point = new Point(x + 1, y + 1);
                }
            }
        }

        return point.x + "," + point.y;
    }

    @Override
    public String solvePart2() {
        final var serialNumber = Integer.parseInt(getPuzzleInput().trim());
        final var cells = getFuelCellPartialSums(serialNumber);

        var maxSum = 0;
        var maxSize = 0;
        var point = new Point(0, 0);
        for (var size = 1; size <= 300; size++) {
            for (var x = 1; x <= 300 - size; x++) {
                for (var y = 1; y <= 300 - size; y++) {
                    var sum = cells[x + size][y + size] - cells[x + size][y] - cells[x][y + size]  + cells[x][y];
                    if (sum >= maxSum) {
                        maxSum = sum;
                        maxSize = size;
                        point = new Point(x + 1, y + 1);
                    }
                }
            }
        }

        return point.x + "," + point.y + "," + maxSize;
    }

    private int[][] getFuelCellPartialSums(final int serialNumber) {
        final var fuelCells = new int[301][301];
        // Initialize fuel cell data
        for (var x = 1; x <= 300; x++) {
            for (var y = 1; y <= 300; y++) {
                final var rackId = x + 10;
                final var power = ((rackId * y + serialNumber) * rackId / 100) % 10 - 5;
                fuelCells[x][y] = power;
            }
        }
        // Partial sums
        for (var x = 1; x <= 300; x++) {
            for (var y = 1; y <= 300; y++) {
                fuelCells[x][y] += fuelCells[x - 1][y] + fuelCells[x][y - 1] - fuelCells[x - 1][y - 1];
            }
        }
        return fuelCells;
    }


}
