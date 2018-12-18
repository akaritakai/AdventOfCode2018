package net.akaritakai.aoc2018;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.RegEx;
import org.jetbrains.annotations.NotNull;

import static net.akaritakai.aoc2018.Problem17.Direction.*;


@SuppressWarnings("Duplicates")
public class Problem17 extends AbstractProblem {
  @Override
  public int getDay() {
    return 17;
  }

  @Override
  public String solvePart1() {
    processInput();
    return String.valueOf(flowing.size());
  }

  @Override
  public String solvePart2() {
    processInput();
    return String.valueOf(settled.size());
  }

  private boolean fill(@NotNull final Point point, @NotNull final Direction direction) {
    flowing.add(point);

    final var below = new Point(point.x, point.y + 1);

    if (!clay.contains(below) && !flowing.contains(below) && below.y <= maxY) {
      fill(below, DOWN);
    }
    if (!clay.contains(below) && !settled.contains(below)) {
      return false;
    }

    var left = new Point(point.x - 1, point.y);
    var right = new Point(point.x + 1, point.y);

    final var leftFilled = clay.contains(left) || !flowing.contains(left) && fill(left, LEFT);
    final var rightFilled = clay.contains(right) || !flowing.contains(right) && fill(right, RIGHT);

    if (direction == DOWN && leftFilled && rightFilled) {
      settled.add(point);
      while (flowing.contains(left)) {
        settled.add(left);
        left = new Point(left.x - 1, left.y);
      }
      while (flowing.contains(right)) {
        settled.add(right);
        right = new Point(right.x + 1, right.y);
      }
    }

    return direction == LEFT && (leftFilled || clay.contains(left))
        || direction == RIGHT && (rightFilled || clay.contains(right));
  }

  private int maxY;
  private Set<Point> clay;
  private Set<Point> flowing;
  private Set<Point> settled;

  private void processInput() {
    final var lines = getPuzzleInput().lines().collect(Collectors.toList());
    @RegEx final var regex = "^[xy]=(\\d+), [xy]=(\\d+)..(\\d+)$";

    // Get all the clay points
    clay = new HashSet<>();
    for (String line : lines) {
      if (line.startsWith("x")) {
        final var x = Integer.parseInt(line.replaceAll(regex, "$1"));
        final var yMin = Integer.parseInt(line.replaceAll(regex, "$2"));
        final var yMax = Integer.parseInt(line.replaceAll(regex, "$3"));
        for (var y = yMin; y <= yMax; y++) {
          clay.add(new Point(x, y));
        }
      }
      if (line.startsWith("y")) {
        final var xMin = Integer.parseInt(line.replaceAll(regex, "$2"));
        final var xMax = Integer.parseInt(line.replaceAll(regex, "$3"));
        final var y = Integer.parseInt(line.replaceAll(regex, "$1"));
        for (var x = xMin; x <= xMax; x++) {
          clay.add(new Point(x, y));
        }
      }
    }

    // Calculate the minimum and maximum depth
    final var minY =  clay.stream().mapToInt(p -> p.y).min().orElseThrow();
    maxY = clay.stream().mapToInt(p -> p.y).max().orElseThrow();

    // Initialize the set of flowing and settled points
    flowing = new HashSet<>();
    settled = new HashSet<>();

    // Fill in the grid
    fill(new Point(500, 0), DOWN);

    // Remove any point that is below our minY
    flowing.removeIf(p -> p.y < minY);
    settled.removeIf(p -> p.y < minY);
  }

  enum Direction {
    DOWN (0, 1),
    LEFT (-1, 0),
    RIGHT (1, 0);

    final int dx;
    final int dy;

    Direction(final int dx, final int dy) {
      this.dx = dx;
      this.dy = dy;
    }
  }
}
