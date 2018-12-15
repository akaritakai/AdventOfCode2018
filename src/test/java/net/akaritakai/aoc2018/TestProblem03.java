package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem03 extends BasePuzzleTest {

  private static final String EXAMPLE_INPUT = String.join("\n", List.of(
      "#1 @ 1,3: 4x4",
      "#2 @ 3,1: 4x4",
      "#3 @ 5,5: 2x2"
  ));

  @Test
  public void testProblemPart1Example1() {
    final var problem = new Problem03();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart1(), "4");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem03();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "118539");
  }

  @Test
  public void testProblemPart2Example1() {
    final var problem = new Problem03();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart2(), "3");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem03();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "1270");
  }
}
