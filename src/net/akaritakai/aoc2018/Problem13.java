package net.akaritakai.aoc2018;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class Problem13 extends AbstractProblem {
  @Override
  public int getDay() {
    return 13;
  }

  @Override
  public String solvePart1() {
    processInput();

    while (crashed.isEmpty()) {
      tick();
    }

    final var collisionPoint = crashed.stream()
        .findFirst()
        .map(cart -> new Point(cart.x, cart.y))
        .orElseThrow();

    return String.format("%d,%d", collisionPoint.x, collisionPoint.y);
  }

  @Override
  public String solvePart2() {
    processInput();

    while (carts.size() > 1) {
      tick();
    }

    final var cartLocation = carts.stream()
        .findAny()
        .map(cart -> new Point(cart.x, cart.y))
        .orElseThrow();

    return String.format("%d,%d", cartLocation.x, cartLocation.y);
  }

  private char track[][];
  private List<Cart> carts;
  private List<Cart> crashed;

  private void tick() {
    // Sort the carts by their movement order
    final var allCarts = carts
        .stream()
        .sorted(Comparator.comparingInt((Cart cart) -> cart.y).thenComparingInt(cart -> cart.x))
        .collect(Collectors.toList());

    // Move the carts one at a time
    for (final var cart1 : allCarts) {
      if (crashed.contains(cart1)) {
        continue; // If we've already crashed, don't move
      }

      // Move the cart
      cart1.moveCart(track);

      // Check for collisions against every other cart
      for (final var cart2 : allCarts) {
        if (cart1 == cart2 || crashed.contains(cart1) || crashed.contains(cart2)) {
          // We can't collide with ourself, or crash again if we've already crashed.
          continue;
        }
        if (cart1.x == cart2.x && cart1.y == cart2.y) {
          // Crash!
          crashed.add(cart1);
          crashed.add(cart2);
        }
      }
    }

    // Remove any carts we marked as crashed
    carts.removeAll(crashed);
  }

  private void processInput() {
    final var lines = getPuzzleInput().lines().collect(Collectors.toList());

    // Process the track
    final var width = lines.stream().mapToInt(String::length).max().orElseThrow();
    final var height = lines.size();
    track = new char[width][height];
    for (var y = 0; y < height; y++) {
      for (var x = 0; x < width; x++) {
        var c = lines.get(y).charAt(x);
        switch (c) {
          case '^': c = '|'; break;
          case 'v': c = '|'; break;
          case '<': c = '-'; break;
          case '>': c = '-'; break;
        }
        track[x][y] = c;
      }
    }

    // Process the carts
    carts = new ArrayList<>();
    for (var y = 0; y < height; y++) {
      for (var x = 0; x < width; x++) {
        final var c = lines.get(y).charAt(x);
        switch (c) {
          case '^': carts.add(new Cart(x, y, Direction.UP)); break;
          case 'v': carts.add(new Cart(x, y, Direction.DOWN)); break;
          case '<': carts.add(new Cart(x, y, Direction.LEFT)); break;
          case '>': carts.add(new Cart(x, y, Direction.RIGHT)); break;
        }
      }
    }

    // Initialize the list of crashes
    crashed = new ArrayList<>();
  }

  static class Cart {
    int x;
    int y;
    Direction direction;
    Turn turn = Turn.LEFT;

    Cart(final int x, final int y, final Direction direction) {
      this.x = x;
      this.y = y;
      this.direction = direction;
    }

    void moveCart(final char[][] track) {
      // Move in the direction we are pointing
      x += direction.dx;
      y += direction.dy;

      switch (track[x][y]) {
        // Change direction at curves
        case '/':
        case '\\':
          direction = direction.curve(track[x][y]);
          break;
        // At intersections, change direction according to our turn state
        case '+':
          direction = direction.turn(turn);
          turn = turn.nextTurn();
          break;
      }
    }
  }

  @SuppressWarnings("Duplicates")
  enum Direction {
    UP(0,-1),
    DOWN(0,1),
    LEFT(-1,0),
    RIGHT(1,0);

    final int dx;
    final int dy;

    Direction(final int dx, final int dy) {
      this.dx = dx;
      this.dy = dy;
    }

    Direction curve(final char track) {
      switch (track) {
        case '/':
          switch (this) {
            case UP: return RIGHT;
            case DOWN: return LEFT;
            case LEFT: return DOWN;
            case RIGHT: return UP;
          }
        case '\\':
          switch (this) {
            case UP: return LEFT;
            case DOWN: return RIGHT;
            case LEFT: return UP;
            case RIGHT: return DOWN;
          }
      }
      throw new RuntimeException();
    }

    Direction turn(final Turn turn) {
      switch (this) {
        case UP:
          switch (turn) {
            case LEFT: return LEFT;
            case STRAIGHT: return UP;
            case RIGHT: return RIGHT;
          }
        case DOWN:
          switch (turn) {
            case LEFT: return RIGHT;
            case STRAIGHT: return DOWN;
            case RIGHT: return LEFT;
          }
        case LEFT:
          switch (turn) {
            case LEFT: return DOWN;
            case STRAIGHT: return LEFT;
            case RIGHT: return UP;
          }
        case RIGHT:
          switch (turn) {
            case LEFT: return UP;
            case STRAIGHT: return RIGHT;
            case RIGHT: return DOWN;
          }
      }
      throw new RuntimeException();
    }
  }

  @SuppressWarnings("Duplicates")
  enum Turn {
    LEFT,
    STRAIGHT,
    RIGHT;

    Turn nextTurn() {
      switch (this) {
        case LEFT: return STRAIGHT;
        case STRAIGHT: return RIGHT;
        case RIGHT: return LEFT;
      }
      throw new RuntimeException();
    }
  }
}
