package net.akaritakai.aoc2018;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem09 extends BasePuzzleTest {

  @Test
  public void testProblemPart1Example1() {
    final var problem = new Problem09();
    problem.setPuzzleInput("9 players; last marble is worth 25 points");
    Assert.assertEquals(problem.solvePart1(), "32");
  }

  @Test
  public void testProblemPart1Example2() {
    final var problem = new Problem09();
    problem.setPuzzleInput("10 players; last marble is worth 1618 points");
    Assert.assertEquals(problem.solvePart1(), "8317");
  }

  @Test
  public void testProblemPart1Example3() {
    final var problem = new Problem09();
    problem.setPuzzleInput("13 players; last marble is worth 7999 points");
    Assert.assertEquals(problem.solvePart1(), "146373");
  }

  @Test
  public void testProblemPart1Example4() {
    final var problem = new Problem09();
    problem.setPuzzleInput("17 players; last marble is worth 1104 points");
    Assert.assertEquals(problem.solvePart1(), "2764");
  }

  @Test
  public void testProblemPart1Example5() {
    final var problem = new Problem09();
    problem.setPuzzleInput("21 players; last marble is worth 6111 points");
    Assert.assertEquals(problem.solvePart1(), "54718");
  }

  @Test
  public void testProblemPart1Example6() {
    final var problem = new Problem09();
    problem.setPuzzleInput("30 players; last marble is worth 5807 points");
    Assert.assertEquals(problem.solvePart1(), "37305");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem09();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "384892");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem09();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "3169872331");
  }
}
