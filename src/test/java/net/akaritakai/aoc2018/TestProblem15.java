package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem15 extends BasePuzzleTest {

  @Test
  public void testProblemPart1Example1() {
    final var problem = new Problem15();
    problem.setPuzzleInput(String.join("\n", List.of(
        "#######",
        "#.G...#",
        "#...EG#",
        "#.#.#G#",
        "#..G#E#",
        "#.....#",
        "#######"
    )));
    Assert.assertEquals(problem.solvePart1(), "27730");
  }

  @Test
  public void testProblemPart1Example2() {
    final var problem = new Problem15();
    problem.setPuzzleInput(String.join("\n", List.of(
        "#######",
        "#G..#E#",
        "#E#E.E#",
        "#G.##.#",
        "#...#E#",
        "#...E.#",
        "#######"
    )));
    Assert.assertEquals(problem.solvePart1(), "36334");
  }

  @Test
  public void testProblemPart1Example3() {
    final var problem = new Problem15();
    problem.setPuzzleInput(String.join("\n", List.of(
        "#######",
        "#E..EG#",
        "#.#G.E#",
        "#E.##E#",
        "#G..#.#",
        "#..E#.#",
        "#######"
    )));
    Assert.assertEquals(problem.solvePart1(), "39514");
  }

  @Test
  public void testProblemPart1Example4() {
    final var problem = new Problem15();
    problem.setPuzzleInput(String.join("\n", List.of(
        "#######",
        "#E.G#.#",
        "#.#G..#",
        "#G.#.G#",
        "#G..#.#",
        "#...E.#",
        "#######"
    )));
    Assert.assertEquals(problem.solvePart1(), "27755");
  }

  @Test
  public void testProblemPart1Example5() {
    final var problem = new Problem15();
    problem.setPuzzleInput(String.join("\n", List.of(
        "#######",
        "#.E...#",
        "#.#..G#",
        "#.###.#",
        "#E#G#G#",
        "#...#G#",
        "#######"
    )));
    Assert.assertEquals(problem.solvePart1(), "28944");
  }

  @Test
  public void testProblemPart1Example6() {
    final var problem = new Problem15();
    problem.setPuzzleInput(String.join("\n", List.of(
        "#########",
        "#G......#",
        "#.E.#...#",
        "#..##..G#",
        "#...##..#",
        "#...#...#",
        "#.G...G.#",
        "#.....G.#",
        "#########"
    )));
    Assert.assertEquals(problem.solvePart1(), "18740");
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem15();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart1(), "190777");
  }

  @Test
  public void testProblemPart2Example1() {
    final var problem = new Problem15();
    problem.setPuzzleInput(String.join("\n", List.of(
        "#######",
        "#.G...#",
        "#...EG#",
        "#.#.#G#",
        "#..G#E#",
        "#.....#",
        "#######"
    )));
    Assert.assertEquals(problem.solvePart2(), "4988");
  }

  @Test
  public void testProblemPart2Example2() {
    final var problem = new Problem15();
    problem.setPuzzleInput(String.join("\n", List.of(
        "#######",
        "#E..EG#",
        "#.#G.E#",
        "#E.##E#",
        "#G..#.#",
        "#..E#.#",
        "#######"
    )));
    Assert.assertEquals(problem.solvePart2(), "31284");
  }

  @Test
  public void testProblemPart2Example3() {
    final var problem = new Problem15();
    problem.setPuzzleInput(String.join("\n", List.of(
        "#######",
        "#E.G#.#",
        "#.#G..#",
        "#G.#.G#",
        "#G..#.#",
        "#...E.#",
        "#######"
    )));
    Assert.assertEquals(problem.solvePart2(), "3478");
  }
  @Test
  public void testProblemPart2Example4() {
    final var problem = new Problem15();
    problem.setPuzzleInput(String.join("\n", List.of(
        "#######",
        "#.E...#",
        "#.#..G#",
        "#.###.#",
        "#E#G#G#",
        "#...#G#",
        "#######"
    )));
    Assert.assertEquals(problem.solvePart2(), "6474");
  }

  @Test
  public void testProblemPart2Example5() {
    final var problem = new Problem15();
    problem.setPuzzleInput(String.join("\n", List.of(
        "#########",
        "#G......#",
        "#.E.#...#",
        "#..##..G#",
        "#...##..#",
        "#...#...#",
        "#.G...G.#",
        "#.....G.#",
        "#########"
    )));
    Assert.assertEquals(problem.solvePart2(), "1140");
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem15();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(problem.solvePart2(), "47388");
  }
}
