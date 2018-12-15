package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem04 extends BasePuzzleTest {

  private static final String EXAMPLE_INPUT = String.join("\n", List.of(
      "[1518-11-01 00:00] Guard #10 begins shift",
      "[1518-11-01 00:05] falls asleep",
      "[1518-11-01 00:25] wakes up",
      "[1518-11-01 00:30] falls asleep",
      "[1518-11-01 00:55] wakes up",
      "[1518-11-01 23:58] Guard #99 begins shift",
      "[1518-11-02 00:40] falls asleep",
      "[1518-11-02 00:50] wakes up",
      "[1518-11-03 00:05] Guard #10 begins shift",
      "[1518-11-03 00:24] falls asleep",
      "[1518-11-03 00:29] wakes up",
      "[1518-11-04 00:02] Guard #99 begins shift",
      "[1518-11-04 00:36] falls asleep",
      "[1518-11-04 00:46] wakes up",
      "[1518-11-05 00:03] Guard #99 begins shift",
      "[1518-11-05 00:45] falls asleep",
      "[1518-11-05 00:55] wakes up"
  ));

  @Test
  public void testProblemPart1Example() {
    final var problem = new Problem04();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart1(), "240");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem04();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "8421");
  }

  @Test
  public void testProblemPart2Example() {
    final var problem = new Problem04();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(problem.solvePart2(), "4455");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem04();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "83359");
  }
}
