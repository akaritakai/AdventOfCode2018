package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem06 extends BasePuzzleTest {

  private static final String EXAMPLE_INPUT = String.join("\n", List.of(
      "1, 1",
      "1, 6",
      "8, 3",
      "3, 4",
      "5, 5",
      "8, 9"
  ));

  @Test
  public void testProblemPart1Example() {
    final var problem = new Problem06();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart1(), "17");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem06();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "3293");
  }

  @Test
  public void testProblemPart2Example() {
    final var problem = new Problem06();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart2(32), "16");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem06();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "45176");
  }
}
