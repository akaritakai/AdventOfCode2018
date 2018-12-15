package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem07 extends BasePuzzleTest {

  private static final String EXAMPLE_INPUT = String.join("\n", List.of(
      "Step C must be finished before step A can begin.",
      "Step C must be finished before step F can begin.",
      "Step A must be finished before step B can begin.",
      "Step A must be finished before step D can begin.",
      "Step B must be finished before step E can begin.",
      "Step D must be finished before step E can begin.",
      "Step F must be finished before step E can begin."
  ));

  @Test
  public void testProblemPart1Example() {
    final var problem = new Problem07();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart1(), "CABDFE");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem07();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "ABDCJLFMNVQWHIRKTEUXOZSYPG");
  }

  @Test
  public void testProblemPart2Example() {
    final var problem = new Problem07();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart2(2, 0), "15");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem07();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "896");
  }
}
