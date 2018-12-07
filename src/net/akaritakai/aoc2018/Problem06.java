package net.akaritakai.aoc2018;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;


public class Problem06 extends AbstractProblem {
  @Override
  public int getDay() {
    return 6;
  }

  @Override
  public String solvePart1() {
    // Fetch our puzzle input as a list of points
    final var points = getPoints();

    // Create a finite plane at least as big as the furthest points from the origin
    final var maxWidth = points.stream().max(Comparator.comparingInt(p -> p.x)).map(point -> point.x).orElse(-1);
    final var maxHeight = points.stream().max(Comparator.comparingInt(p -> p.y)).map(point -> point.y).orElse(-1);
    final var plane = new Point[maxWidth + 1][maxHeight + 1];

    // For every point in our finite plane, tile it with the point it is uniquely close to in our list
    // Also, for every point, keep track of its region size
    final Map<Point, Long> regions = new HashMap<>();
    for (var x = 0; x <= maxWidth; x++) {
      for (var y = 0; y <= maxHeight; y++) {
        final var point = new Point(x, y);
        final var closestPoint = findClosestPoint(point, points);
        if (closestPoint != null) {
          plane[x][y] = closestPoint;
          regions.put(closestPoint, regions.getOrDefault(closestPoint, 0L) + 1);
        }
      }
    }

    // Remove regions that are infinitely large
    for (var x = 0; x <= maxWidth; x++) {
      regions.remove(plane[x][0]);
      regions.remove(plane[x][maxHeight]);
    }
    for (var y = 0; y <= maxHeight; y++) {
      regions.remove(plane[0][y]);
      regions.remove(plane[maxWidth][y]);
    }

    // Get the size of the largest region
    final var largestRegion = regions
        .entrySet()
        .stream()
        .map(Map.Entry::getValue)
        .max(Long::compare);

    return largestRegion.map(String::valueOf).orElse(null);
  }

  @Override
  public String solvePart2() {
    // Fetch our puzzle input as a list of points
    final var points = getPoints();

    // Create a finite plane at least as big as the furthest points from the origin
    final var maxWidth = points.stream().max(Comparator.comparingInt(p -> p.x)).map(point -> point.x).orElse(-1);
    final var maxHeight = points.stream().max(Comparator.comparingInt(p -> p.y)).map(point -> point.y).orElse(-1);

    var area = 0; // The area of points where the distance to every point on our list sums to less than 10,000

    // Check every point on our finite plane
    for (var x = 0; x <= maxWidth; x++) {
      for (var y = 0; y <= maxHeight; y++) {
        final var point = new Point(x, y);
        final var distanceSum = points.stream()
            .mapToLong(p -> distance(p, point))
            .sum();
        if (distanceSum < 10_000) {
          area++;
        }
      }
    }

    return String.valueOf(area);
  }

  /**
   * Returns the closest point from a list of points to the given point, but only if it is uniquely close
   */
  private Point findClosestPoint(@NotNull final Point point, @NotNull final List<Point> allPoints) {
    // Find the closest point
    final var closestPoint = allPoints.stream()
        .filter(p -> !p.equals(point))
        .min(Comparator.comparingLong(p -> distance(p, point)))
        .orElseThrow();

    // Determine if that point is uniquely close
    final var isUnique = allPoints.stream()
        .filter(p -> !p.equals(point))
        .filter(p -> !p.equals(closestPoint))
        .noneMatch(p -> distance(point, p) == distance(point, closestPoint));

    // Return the closest point only if it is unique
    return isUnique ? closestPoint : null;
  }

  /**
   * Gets the Manhattan distance between two points.
   */
  private long distance(@NotNull final Point a, @NotNull final Point b) {
    return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
  }

  private List<Point> getPoints() {
    return getPuzzleInputLines()
        .stream()
        .map(line -> {
          @RegExp final var regex = "^(\\d+), (\\d+)$";
          final var x = Integer.parseInt(line.replaceAll(regex, "$1"));
          final var y = Integer.parseInt(line.replaceAll(regex, "$2"));
          return new Point(x, y);
        })
        .collect(Collectors.toList());
  }
}
