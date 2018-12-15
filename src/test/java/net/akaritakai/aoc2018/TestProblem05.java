package net.akaritakai.aoc2018;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem05 extends BasePuzzleTest {

  @Test
  public void testProblemPart1Example() {
    final var problem = new Problem05();
    problem.setPuzzleInput("aA");
    Assert.assertEquals(problem.solvePart1(), "0");
    problem.setPuzzleInput("abBA");
    Assert.assertEquals(problem.solvePart1(), "0");
    problem.setPuzzleInput("aabAAB");
    Assert.assertEquals(problem.solvePart1(), "6");
    problem.setPuzzleInput("dabAcCaCBAcCcaDA");
    Assert.assertEquals(problem.solvePart1(), "10");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem05();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "11108");
  }

  @Test
  public void testProblemPart2Example() {
    final var problem = new Problem05();
    problem.setPuzzleInput("dabAcCaCBAcCcaDA");
    Assert.assertEquals(problem.solvePart2(), "4");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem05();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "5094");
  }
}
