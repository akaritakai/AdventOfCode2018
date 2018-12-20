package net.akaritakai.aoc2018;

import com.google.common.collect.Lists;
import java.awt.Point;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


@SuppressWarnings("Duplicates")
public class Problem15 extends AbstractProblem {
  @Override
  public int getDay() {
    return 15;
  }

  @Override
  public String solvePart1() {
    processInput(3);

    // Simulate combat
    while(!combatOver()) {
      simulateRound();
    }

    return String.valueOf(getOutcome());
  }

  @Override
  public String solvePart2() {
    /*
     * Unfortunately, checking to see if a particular elven attack power satisfies this problem is expensive.
     * Thus, we should minimize the number of tests we need to do in order to find our answer.
     *
     * Our total search space is from 4 (our given minimum) to 200 (at which elves one-shot goblins) since there is no
     * benefit to survivability once they are that powerful.
     *
     * We will use binary search to aggressively check our search space, and we will do so in parallel for an extra
     * boost.
     */
    final var powers = IntStream.rangeClosed(4, 200).boxed().collect(Collectors.toList());

    int minSuccessPower = Integer.MAX_VALUE;
    int maxFailedPower = Integer.MIN_VALUE;

    final var numCores = Runtime.getRuntime().availableProcessors();

    final var outcomes = new ConcurrentHashMap<>();
    final var successes = new ConcurrentLinkedQueue<Integer>();
    final var failures = new ConcurrentLinkedQueue<Integer>();
    final var removals = new ConcurrentLinkedQueue<Predicate<Integer>>();

    while (maxFailedPower + 1 != minSuccessPower && !powers.isEmpty()) {
      // Parallelize the search
      Lists.partition(powers, (powers.size() / numCores) + 1).parallelStream().forEach(searchSpace -> {
        // Pick the midpoint of our assigned search space
        final var power = searchSpace.get(searchSpace.size() / 2);

        if (outcomes.containsKey(power)) {
          return; // Skip if we've already tested this
        }

        // Test it
        final var problem = new Problem15();
        problem.setPuzzleInput(getPuzzleInput());
        problem.processInput(power);
        final var numElves = problem.elves.size();
        while (!problem.combatOver()) {
          problem.simulateRound();
        }

        if (problem.elves.size() == numElves) {
          // Elves survived
          successes.add(power);
          outcomes.put(power, problem.getOutcome());
          removals.add(i -> i >= power);
        } else {
          // Elves did not survive
          failures.add(power);
          removals.add(i -> i <= power);
        }
      });

      // Aggregate the parallelized search results
      removals.forEach(powers::removeIf);
      while (!successes.isEmpty()) {
        minSuccessPower = Math.min(minSuccessPower, successes.poll());
      }
      while (!failures.isEmpty()) {
        maxFailedPower = Math.max(maxFailedPower, failures.poll());
      }
    }

    return String.valueOf(outcomes.get(minSuccessPower));
  }

  /**
   * Checks if combat is over. Happens when either all the elves or all the goblins have been slain
   */
  private boolean combatOver() {
    return elves.isEmpty() || goblins.isEmpty();
  }

  /**
   * Gets the outcome of the combat
   */
  private int getOutcome() {
    final var totalHp = Stream.of(elves, goblins).flatMap(Collection::stream).mapToInt(unit -> unit.hitPoints).sum();
    return rounds * totalHp;
  }

  /**
   * How many rounds of combat we have seen so far
   */
  private int rounds;

  /**
   * The cave's layout
   */
  private char[][] cave;

  /**
   * The list of alive elves
   */
  private List<Unit> elves;

  /**
   * The list of alive goblins
   */
  private List<Unit> goblins;

  /**
   * A cached of currently occupied points (occupied by elves or goblins)
   */
  private Set<Point> occupiedPoints;

  /**
   * A cache of adjacent points from a given point
   */
  private Map<Point, List<Point>> cachedAdjacentPoints;

  /**
   * Simulates a round of combat.
   */
  private void simulateRound() {
    // Get all the units that are alive at the start of the round
    final var startingUnits = Stream.of(elves, goblins)
        .flatMap(Collection::stream)
        .sorted(UNIT_READING_ORDER)
        .collect(Collectors.toList());

    // Simulate each unit's turn
    for (final var unit : startingUnits) {
      // Check if combat is over
      if (combatOver()) {
        return;
      }

      // Check if the unit is alive
      if (!elves.contains(unit) && !goblins.contains(unit)) {
        continue;
      }

      // Try to see if there's someone next to us that we can attack
      if (attack(unit)) {
        continue; // We successfully attacked
      }

      // If there's no one we could attack, we have to move so that we are within attack range and then attack
      move(unit);
      attack(unit);
    }

    rounds++; // We completed the round without combat ending
  }

  /**
   * Moves the unit according to its movement rules.
   */
  private void move(@NotNull final Unit unit) {
    // Get our list of enemies
    final var enemies = new ArrayList<Unit>();
    if (elves.contains(unit)) {
      enemies.addAll(goblins);
    }
    if (goblins.contains(unit)) {
      enemies.addAll(elves);
    }

    final var pathFinder = new PathFinder(cave, occupiedPoints, unit.position);
    pathFinder.calculateMove(enemies).ifPresent(newPosition -> {
      occupiedPoints.remove(unit.position);
      unit.position = newPosition;
      occupiedPoints.add(unit.position);
    });
  }

  /**
   * Causes the unit to attack according to its attack rules.
   * Returns true if it successfully attacked another unit.
   */
  private boolean attack(@NotNull final Unit unit) {
    // Get our list of enemies
    final var enemies = new ArrayList<Unit>();
    if (elves.contains(unit)) {
      enemies.addAll(goblins);
    }
    if (goblins.contains(unit)) {
      enemies.addAll(elves);
    }

    // Find if any of our enemies are adjacent to us
    final var adjacentEnemies = enemies.stream()
        .filter(enemy -> getAdjacentPoints(unit.position)
            .stream()
            .anyMatch(point -> enemy.position.x == point.x && enemy.position.y == point.y))
        .collect(Collectors.toList());

    if (adjacentEnemies.isEmpty()) {
      return false; // There are no enemies adjacent to us to attack
    }

    // Find the enemy with the fewest hit points
    final var preferredEnemy = adjacentEnemies.stream()
        // Group enemies by their hit points
        .collect(Collectors.groupingBy(enemy -> enemy.hitPoints, Collectors.toList())).entrySet().stream()
        // Get enemies who have the fewest hit points
        .min(Comparator.comparingInt(Map.Entry::getKey)).map(Map.Entry::getValue).orElse(Collections.emptyList()).stream()
        // Tie-break using reading order
        .min(UNIT_READING_ORDER).orElseThrow();

    // Attack!
    preferredEnemy.hitPoints -= unit.attackPower;

    // Check for and resolve enemy deaths
    if (preferredEnemy.hitPoints <= 0) {
      elves.remove(preferredEnemy);
      goblins.remove(preferredEnemy);
      occupiedPoints.remove(preferredEnemy.position);
    }

    return true;
  }

  /**
   * Returns all points adjacent to the given point.
   */
  private List<Point> getAdjacentPoints(@NotNull final Point point) {
    if (!cachedAdjacentPoints.containsKey(point)) {
      final var adjacentPoints = new ArrayList<Point>(4);
      // Test if the point to the left is reachable
      if (point.x - 1 >= 0 && cave[point.x - 1][point.y] != '#') {
        adjacentPoints.add(new Point(point.x - 1, point.y));
      }
      // Test if the point to the right is reachable
      if (point.x + 1 < cave.length && cave[point.x + 1][point.y] != '#') {
        adjacentPoints.add( new Point(point.x + 1, point.y));
      }
      // Test if the point up is reachable
      if (point.y - 1 >= 0 && cave[point.x][point.y - 1] != '#') {
        adjacentPoints.add(new Point(point.x, point.y - 1));
      }
      // Test if the point down is reachable
      if (point.y + 1 < cave[0].length && cave[point.x][point.y + 1] != '#') {
        adjacentPoints.add(new Point(point.x, point.y + 1));
      }
      cachedAdjacentPoints.put(point, adjacentPoints);
    }
    return cachedAdjacentPoints.get(point);
  }

  /**
   * Processes the input given the provided elven attack power.
   */
  private void processInput(final int elvenAttackPower) {
    final var lines = getPuzzleInput().lines().collect(Collectors.toList());

    final var width = lines.stream().mapToInt(String::length).max().orElseThrow();
    final var height = lines.size();

    // Process cave structure
    cave = new char[width][height];
    for (var y = 0; y < height; y++) {
      for (var x = 0; x < width; x++) {
        if (lines.get(y).charAt(x) == '#') {
          cave[x][y] = '#';
        } else {
          cave[x][y] = '.';
        }
      }
    }

    // Process elves and goblins
    elves = new ArrayList<>();
    goblins = new ArrayList<>();
    occupiedPoints = new HashSet<>();
    for (var y = 0; y < height; y++) {
      for (var x = 0; x < width; x++) {
        switch (lines.get(y).charAt(x)) {
          case 'E':
            final var elfLocation = new Point(x, y);
            occupiedPoints.add(elfLocation);
            final var elf = new Unit(elfLocation, elvenAttackPower);
            elves.add(elf);
            break;
          case 'G':
            final var goblinLocation = new Point(x, y);
            occupiedPoints.add(goblinLocation);
            final var goblin = new Unit(goblinLocation);
            goblins.add(goblin);
            break;
        }
      }
    }

    // Initialize the number of rounds
    rounds = 0;

    // Initialize a cache for adjacent points
    cachedAdjacentPoints = new ConcurrentHashMap<>();
  }

  /**
   * Unit defines an elf or goblin with a given position, hit points, and attack power.
   */
  static class Unit {
    private static final int GOBLIN_ATTACK_POWER = 3;

    Point position;
    int hitPoints = 200;
    final int attackPower;

    Unit(Point position) {
      this(position, GOBLIN_ATTACK_POWER);
    }

    Unit(Point position, int attackPower) {
      this.position = position;
      this.attackPower = attackPower;
    }
  }

  /**
   * Class used for determining the ideal route between two points in the cave.
   */
  static class PathFinder {
    private final char[][] cave;
    private final Set<Point> occupiedPoints;
    private final Graph<Point, DefaultEdge> graph;
    private final Point startingPoint;

    PathFinder(@NotNull final char[][] cave, @NotNull final Set<Point> occupiedPoints, @NotNull final Point startingPoint) {
      this.cave = cave;
      this.occupiedPoints = occupiedPoints;
      this.startingPoint = startingPoint;

      // Build the graph
      graph = new DefaultDirectedGraph<>(DefaultEdge.class);
      final var visited = new HashSet<Point>();
      final var stack = new Stack<Point>();
      stack.push(startingPoint);
      while (!stack.isEmpty()) {
        final var p = stack.pop();
        if (visited.add(p)) {
          graph.addVertex(p);
          for (final var adjacent : getReachableAdjacentEdges(p)) {
            graph.addVertex(adjacent);
            graph.addEdge(p, adjacent);
            stack.push(adjacent);
          }
        }
      }
    }

    /**
     * Gets the neighbors around a point
     */
    final Function<Point, Stream<Point>> neighbors = p -> Stream.of(
            new Point(p.x - 1, p.y),
            new Point(p.x + 1, p.y),
            new Point(p.x, p.y - 1),
            new Point(p.x, p.y + 1));

    /**
     * Calculates the best move to make to reach the given enemies
     */
    Optional<Point> calculateMove(@NotNull final List<Unit> enemies) {
      final var dijkstra = new DijkstraShortestPath<>(graph);
      final var paths = new ConcurrentHashMap<Point, SingleSourcePaths<Point, DefaultEdge>>();

      // Get the points surrounding enemies
      return enemies.stream().map(enemy -> enemy.position).flatMap(neighbors)
              // Remove any unreachable points
              .filter(graph::containsVertex)
              .filter(point -> paths.computeIfAbsent(startingPoint, s -> dijkstra.getPaths(startingPoint)).getPath(point) != null)
              // Collect the points into a distance map
              .collect(Collectors.groupingBy(point -> paths.get(startingPoint).getPath(point).getLength(), Collectors.toList())).entrySet().stream()
              // Get the points that have the shortest distance
              .min(Comparator.comparingInt(Map.Entry::getKey)).map(Map.Entry::getValue).orElse(Collections.emptyList()).stream()
              // Tie-break points by reading order
              .min(POINT_READING_ORDER)
              .map(destination ->
                      // Get the points surrounding the starting point
                      neighbors.apply(startingPoint)
                              // Remove any unreachable points
                              .filter(graph::containsVertex)
                              .filter(point -> paths.computeIfAbsent(destination, s -> dijkstra.getPaths(destination)).getPath(point) != null)
                              // Collect the points into a map of how fast they are
                              .collect(Collectors.groupingBy(point -> paths.get(destination).getPath(point).getLength(), Collectors.toList())).entrySet().stream()
                              // Get the points that get to our destination the fastest
                              .min(Comparator.comparingInt(Map.Entry::getKey)).map(Map.Entry::getValue).orElse(Collections.emptyList()).stream()
                              // Tie-break points by reading order
                              .min(POINT_READING_ORDER).orElseThrow());
    }

    /**
     * Get all reachable adjacent edges from the given point
     */
    List<Point> getReachableAdjacentEdges(@NotNull final Point point) {
        final var adjacentPoints = new ArrayList<Point>(4);
        // Test if the point to the left is reachable
        if (point.x - 1 >= 0 && cave[point.x - 1][point.y] != '#') {
          final var p = new Point(point.x - 1, point.y);
          if (!occupiedPoints.contains(p)) {
            adjacentPoints.add(p);
          }
        }
        // Test if the point to the right is reachable
        if (point.x + 1 < cave.length && cave[point.x + 1][point.y] != '#') {
          final var p = new Point(point.x + 1, point.y);
          if (!occupiedPoints.contains(p)) {
            adjacentPoints.add(p);
          }
        }
        // Test if the point above is reachable
        if (point.y - 1 >= 0 && cave[point.x][point.y - 1] != '#') {
          final var p = new Point(point.x, point.y - 1);
          if (!occupiedPoints.contains(p)) {
            adjacentPoints.add(p);
          }
        }
        // Test if the point below is reachable
        if (point.y + 1 < cave[0].length && cave[point.x][point.y + 1] != '#') {
          final var p = new Point(point.x, point.y + 1);
          if (!occupiedPoints.contains(p)) {
            adjacentPoints.add(p);
          }
        }
        return adjacentPoints;
    }
  }

  /**
   * Compares points by their reading order.
   */
  private static final Comparator<Point> POINT_READING_ORDER = Comparator
      .comparingInt((Point point) -> point.y)
      .thenComparingInt(point -> point.x);

  /**
   * Compares units by their reading order.
   */
  private static final Comparator<Unit> UNIT_READING_ORDER = Comparator
      .comparingInt((Unit unit) -> unit.position.y)
      .thenComparingInt(unit -> unit.position.x);
}
