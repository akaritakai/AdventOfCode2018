package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem12 extends BasePuzzleTest {

  private static final String EXAMPLE_INPUT = String.join("\n", List.of(
      "initial state: #..#.#..##......###...###",
      "",
      "...## => #",
      "..#.. => #",
      ".#... => #",
      ".#.#. => #",
      ".#.## => #",
      ".##.. => #",
      ".#### => #",
      "#.#.# => #",
      "#.### => #",
      "##.#. => #",
      "##.## => #",
      "###.. => #",
      "###.# => #",
      "####. => #"
  ));

  @Test
  public void testProblemPart1Example() {
    final var problem = new Problem12();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart1(), "325");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem12();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "1696");
  }

  @Test
  @SuppressWarnings("EmptyMethod")
  public void testProblemPart2Example() {
    // No examples were given for Part 2
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem12();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "1799999999458");
  }
}
