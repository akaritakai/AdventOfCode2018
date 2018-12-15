package net.akaritakai.aoc2018;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem14 extends BasePuzzleTest {

  @Test
  public void testProblemPart1Example1() {
    final var problem = new Problem14();
    problem.setPuzzleInput("9");
    Assert.assertEquals(problem.solvePart1(), "5158916779");
  }

  @Test
  public void testProblemPart1Example2() {
    final var problem = new Problem14();
    problem.setPuzzleInput("5");
    Assert.assertEquals(problem.solvePart1(), "0124515891");
  }

  @Test
  public void testProblemPart1Example3() {
    final var problem = new Problem14();
    problem.setPuzzleInput("18");
    Assert.assertEquals(problem.solvePart1(), "9251071085");
  }

  @Test
  public void testProblemPart1Example4() {
    final var problem = new Problem14();
    problem.setPuzzleInput("2018");
    Assert.assertEquals(problem.solvePart1(), "5941429882");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem14();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "1191216109");
  }

  @Test
  public void testProblemPart2Example1() {
    final var problem = new Problem14();
    problem.setPuzzleInput("51589");
    Assert.assertEquals(problem.solvePart2(), "9");
  }

  @Test
  public void testProblemPart2Example2() {
    final var problem = new Problem14();
    problem.setPuzzleInput("01245");
    Assert.assertEquals(problem.solvePart2(), "5");
  }

  @Test
  public void testProblemPart2Example3() {
    final var problem = new Problem14();
    problem.setPuzzleInput("92510");
    Assert.assertEquals(problem.solvePart2(), "18");
  }

  @Test
  public void testProblemPart2Example4() {
    final var problem = new Problem14();
    problem.setPuzzleInput("59414");
    Assert.assertEquals(problem.solvePart2(), "2018");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem14();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "20268576");
  }
}
