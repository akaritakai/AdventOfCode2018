package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem18 extends BasePuzzleTest {

  @Test
  public void testProblemPart1Example1() {
    final var problem = new Problem18();
    problem.setPuzzleInput(String.join("\n", List.of(
        ".#.#...|#.",
        ".....#|##|",
        ".|..|...#.",
        "..|#.....#",
        "#.#|||#|#|",
        "...#.||...",
        ".|....|...",
        "||...#|.#|",
        "|.||||..|.",
        "...#.|..|."
    )));
    Assert.assertEquals(problem.solvePart1(), "1147");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem18();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "549936");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem18();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "206304");
  }

}
