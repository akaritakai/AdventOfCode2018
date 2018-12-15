package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem02 extends BasePuzzleTest {

  @Test
  public void testProblemPart1Example1() {
    final var problem = new Problem02();
    problem.setPuzzleInput(String.join("\n", List.of(
        "abcdef",
        "bababc",
        "abbcde",
        "abcccd",
        "aabcdd",
        "abcdee",
        "ababab"
    )));
    Assert.assertEquals(problem.solvePart1(), "12");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem02();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "7470");
  }

  @Test
  public void testProblemPart2Example1() {
    final var problem = new Problem02();
    problem.setPuzzleInput(String.join("\n", List.of(
        "abcde",
        "fghij",
        "klmno",
        "pqrst",
        "fguij",
        "axcye",
        "wvxyz"
    )));
    Assert.assertEquals(problem.solvePart2(), "fgij");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem02();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "kqzxdenujwcstybmgvyiofrrd");
  }
}
