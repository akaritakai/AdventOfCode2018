package net.akaritakai.aoc2018;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem08 extends BasePuzzleTest {

  private static final String EXAMPLE_INPUT = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";

  @Test
  public void testProblemPart1Example() {
    final var problem = new Problem08();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart1(), "138");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem08();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "49426");
  }

  @Test
  public void testProblemPart2Example() {
    final var problem = new Problem08();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart2(), "66");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem08();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "40688");
  }
}
