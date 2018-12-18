package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem16 extends BasePuzzleTest {

  @Test
  public void testProblemPart1Example1() {
    final var problem = new Problem16();
    problem.setPuzzleInput(String.join("\n", List.of(
        "Before: [3, 2, 1, 1]",
        "9 2 1 2",
        "After:  [3, 2, 2, 1]"
    )));
    Assert.assertEquals(problem.solvePart1(), "1");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem16();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "646");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem16();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "681");
  }

}
