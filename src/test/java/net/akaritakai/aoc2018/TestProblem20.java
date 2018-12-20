package net.akaritakai.aoc2018;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem20 extends BasePuzzleTest {

  @Test
  public void testProblemPart1Example1() {
    final var problem = new Problem20();
    problem.setPuzzleInput("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$");
    Assert.assertEquals(problem.solvePart1(), "23");
  }

  @Test
  public void testProblemPart1Example2() {
    final var problem = new Problem20();
    problem.setPuzzleInput("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$");
    Assert.assertEquals(problem.solvePart1(), "31");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem20();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "3527");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem20();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "8420");
  }

}
