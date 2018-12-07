package net.akaritakai.aoc2018;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;


public class Problem6 extends AbstractProblem {
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
        final var closestPoints = findClosestPoints(point, points);
        if (closestPoints.size() == 1) {
          var closestPoint = closestPoints.get(0);
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
   * Gets the list of points closest to the given point from the provided list of points.
   */
  private List<Point> findClosestPoints(@NotNull final Point point, @NotNull final List<Point> allPoints) {
    // Find the smallest distance
    final var smallestDistance = allPoints.stream()
        .filter(p -> !p.equals(point))
        .map(p -> distance(p, point))
        .min(Long::compare)
        .orElse(-1L);

    // Return points that have the smallest distance
    return allPoints.stream()
        .filter(p -> !p.equals(point))
        .filter(p -> distance(p, point) == smallestDistance)
        .collect(Collectors.toList());
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
