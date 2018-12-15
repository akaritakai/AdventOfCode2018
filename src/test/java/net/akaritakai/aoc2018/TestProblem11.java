package net.akaritakai.aoc2018;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem11 extends BasePuzzleTest {

  @Test
  public void testProblemPart1Example() {
    final var problem = new Problem11();
    problem.setPuzzleInput("18");
    Assert.assertEquals(problem.solvePart1(), "33,45");
    problem.setPuzzleInput("42");
    Assert.assertEquals(problem.solvePart1(), "21,61");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem11();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "241,40");
  }

  @Test
  public void testProblemPart2Example() {
    final var problem = new Problem11();
    problem.setPuzzleInput("18");
    Assert.assertEquals(problem.solvePart2(), "90,269,16");
    problem.setPuzzleInput("42");
    Assert.assertEquals(problem.solvePart2(), "232,251,12");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem11();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "166,75,12");
  }
}
