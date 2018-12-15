package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem13 extends BasePuzzleTest {

  @Test
  public void testProblemPart1Example() {
    final var problem = new Problem13();
    problem.setPuzzleInput(String.join("\n", List.of(
        "/->-\\        ",
        "|   |  /----\\",
        "| /-+--+-\\  |",
        "| | |  | v  |",
        "\\-+-/  \\-+--/",
        "  \\------/   "
    )));
    Assert.assertEquals(problem.solvePart1(), "7,3");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem13();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "45,34");
  }

  @Test
  public void testProblemPart2Example() {
    final var problem = new Problem13();
    problem.setPuzzleInput(String.join("\n", List.of(
        "/>-<\\  ",
        "|   |  ",
        "| /<+-\\",
        "| | | v",
        "\\>+</ |",
        "  |   ^",
        "  \\<->/"
    )));
    Assert.assertEquals(problem.solvePart2(), "6,4");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem13();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "91,25");
  }
}
