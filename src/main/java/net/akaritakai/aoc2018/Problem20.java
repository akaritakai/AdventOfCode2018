package net.akaritakai.aoc2018;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class Problem20 extends AbstractProblem {
  @Override
  public int getDay() {
    return 20;
  }

  @Override
  public String solvePart1() {
    processInput();
    return distances.values().stream().max(Integer::compare).map(String::valueOf).orElseThrow();
  }

  @Override
  public String solvePart2() {
    processInput();
    final var count = distances.values().stream().filter(i -> i >= 1000).count();
    return String.valueOf(count);
  }

  private Map<Point, Integer> distances;

  private void processInput() {
    final var input = getPuzzleInput().trim().replace("^", "").replace("$", "");
    final var positions = new Stack<Point>();
    distances = new HashMap<>();
    distances.put(new Point(0, 0), 0);
    var current = new Point(0, 0);
    var previous = new Point(0, 0);
    for (char c : input.toCharArray()) {
      switch (c) {
        case '(':
          positions.push(new Point(current.x, current.y));
          break;
        case ')':
          current = positions.pop();
          break;
        case '|':
          current = positions.peek();
          break;
        default: {
          switch (c) {
            case 'N':
              current = new Point(current.x, current.y - 1);
              break;
            case 'S':
              current = new Point(current.x, current.y + 1);
              break;
            case 'E':
              current = new Point(current.x + 1, current.y);
              break;
            case 'W':
              current = new Point(current.x - 1, current.y);
              break;
          }
          final var proposed = distances.get(previous) + 1;
          distances.compute(current, (k, v) -> (v == null) ? proposed : Math.min(v, proposed));
          break;
        }
      }
      previous = new Point(current.x, current.y);
    }
  }


}
