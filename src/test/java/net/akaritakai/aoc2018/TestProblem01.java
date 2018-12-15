package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem01 extends BasePuzzleTest {

  @Test
  public void testProblemPart1Example() {
    final var problem = new Problem01();
    problem.setPuzzleInput(String.join("\n", List.of("+1", "-2", "+3", "+1")));
    Assert.assertEquals(problem.solvePart1(), "3");
    problem.setPuzzleInput(String.join("\n", List.of("+1", "+1", "+1")));
    Assert.assertEquals(problem.solvePart1(), "3");
    problem.setPuzzleInput(String.join("\n", List.of("+1", "+1", "-2")));
    Assert.assertEquals(problem.solvePart1(), "0");
    problem.setPuzzleInput(String.join("\n", List.of("-1", "-2", "-3")));
    Assert.assertEquals(problem.solvePart1(), "-6");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem01();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "533");
  }

  @Test
  public void testProblemPart2Example() {
    final var problem = new Problem01();
    problem.setPuzzleInput(String.join("\n", List.of("+1", "-2", "+3", "+1")));
    Assert.assertEquals(problem.solvePart2(), "2");
    problem.setPuzzleInput(String.join("\n", List.of("+1", "-1")));
    Assert.assertEquals(problem.solvePart2(), "0");
    problem.setPuzzleInput(String.join("\n", List.of("+3", "+3", "+4", "-2", "-4")));
    Assert.assertEquals(problem.solvePart2(), "10");
    problem.setPuzzleInput(String.join("\n", List.of("-6", "+3", "+8", "+5", "-6")));
    Assert.assertEquals(problem.solvePart2(), "5");
    problem.setPuzzleInput(String.join("\n", List.of("+7", "+7", "-2", "-7", "-4")));
    Assert.assertEquals(problem.solvePart2(), "14");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem01();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "73272");
  }
}
