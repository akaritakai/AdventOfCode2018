package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem19 extends BasePuzzleTest {

  @Test
  public void testProblemPart1Example1() {
    final var problem = new Problem19();
    problem.setPuzzleInput(String.join("\n", List.of(
        "#ip 0",
        "seti 5 0 1",
        "seti 6 0 2",
        "addi 0 1 0",
        "addr 1 2 3",
        "setr 1 0 0",
        "seti 8 0 4",
        "seti 9 0 5"
    )));
    Assert.assertEquals(problem.solvePart1(), "7");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem19();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "2240");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem19();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "26671554");
  }

}
