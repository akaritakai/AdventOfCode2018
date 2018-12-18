package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem17 extends BasePuzzleTest {

  private static final String EXAMPLE_INPUT = String.join("\n", List.of(
      "x=495, y=2..7",
      "y=7, x=495..501",
      "x=501, y=3..7",
      "x=498, y=2..4",
      "x=506, y=1..2",
      "x=498, y=10..13",
      "x=504, y=10..13",
      "y=13, x=498..504"
  ));

  @Test
  public void testProblemPart1Example1() {
    final var problem = new Problem17();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart1(), "57");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem17();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "44743");
  }

  @Test
  public void testProblemPart2Example1() {
    final var problem = new Problem17();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart2(), "29");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem17();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "34172");
  }

}
