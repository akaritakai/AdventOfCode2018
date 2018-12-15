package net.akaritakai.aoc2018;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;


public class Problem03 extends AbstractProblem {
  @Override
  public int getDay() {
    return 3;
  }

  @Override
  public String solvePart1() {
    final var count = getClaims().stream()
        .flatMap(claim -> claim.getPoints().stream())
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .values()
        .stream()
        .filter(v -> v > 1)
        .count();
    return String.valueOf(count);
  }

  @Override
  public String solvePart2() {
    // Assume any claim could be a candidate
    final var candidateClaims = getClaims();

    // Speed-up: Test for overlaps within quadrants and remove any matches from our list of candidates
    final var claimArea = getClaimArea(candidateClaims);
    final var overlappingClaims = divideIntoQuadrants(claimArea).parallelStream()
        .flatMap(region -> getOverlappingClaims(candidateClaims, region).stream())
        .collect(Collectors.toSet());
    candidateClaims.removeAll(overlappingClaims);

    // Find the claim that doesn't overlap any other
    final var claim = candidateClaims.parallelStream()
        .filter(claim1 -> getClaims().parallelStream()
            .filter(claim2 -> claim1.id != claim2.id)
            .noneMatch(claim2 -> claim1.rectangle.intersects(claim2.rectangle)))
        .findAny();

    // Return the claim's id
    return claim.map(c -> c.id).map(String::valueOf).orElseThrow();
  }

  /**
   * Determine the claim area based on the claims provided
   */
  private Rectangle getClaimArea(@NotNull final Set<Claim> claims) {
    final var maxWidth = claims.stream()
        .map(claim -> claim.rectangle.x + claim.rectangle.width)
        .max(Integer::compare)
        .orElseThrow();
    final var maxHeight = claims.stream()
        .map(claim -> claim.rectangle.y + claim.rectangle.height)
        .max(Integer::compare)
        .orElseThrow();

    return new Rectangle(0, 0, maxWidth, maxHeight);
  }

  /**
   * Divides a given rectangle into its quadrants
   */
  private List<Rectangle> divideIntoQuadrants(@NotNull final Rectangle region) {
    return List.of(new Rectangle(0, 0, region.width / 2, region.height / 2),
        new Rectangle(0, region.height / 2, region.width / 2, region.height / 2),
        new Rectangle(region.width / 2, 0, region.width / 2, region.height / 2),
        new Rectangle(region.width / 2, region.height / 2, region.width / 2, region.height / 2));
  }

  /**
   * Gets the claims in the region bound by the given rectangle and find the overlapping claims
   */
  private Set<Claim> getOverlappingClaims(@NotNull final Set<Claim> claims, @NotNull final Rectangle region) {
    final var subClaims = claims.parallelStream()
        .filter(claim -> region.contains(claim.rectangle))
        .collect(Collectors.toSet());
    return getOverlappingClaims(subClaims);
  }

  /**
   * Gets the set of claims that overlap another in a given set
   */
  private Set<Claim> getOverlappingClaims(@NotNull final Set<Claim> claims) {
    return claims.parallelStream()
        .filter(claim1 -> claims.parallelStream()
            .filter(claim2 -> !Objects.equals(claim1, claim2))
            .anyMatch(claim2 -> claim1.rectangle.intersects(claim2.rectangle)))
        .collect(Collectors.toSet());
  }

  private Set<Claim> getClaims() {
    return getPuzzleInput().lines().map(Claim::fromString).collect(Collectors.toSet());
  }

  @SuppressWarnings("Duplicates")
  static class Claim {
    final int id;
    final Rectangle rectangle;

    Claim(final int id, @NotNull final Rectangle rectangle) {
      this.id = id;
      this.rectangle = rectangle;
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

    List<Point> getPoints() {
      final List<Point> points = new ArrayList<>();
      for (var x = 0; x < rectangle.width; x++) {
        for (var y = 0; y < rectangle.height; y++) {
          points.add(new Point(rectangle.x + x, rectangle.y + y));
        }
      }
      return points;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Claim claim = (Claim) o;
      return id == claim.id;
    }

    @Override
    public int hashCode() {
      return id;
    }
  }
}
