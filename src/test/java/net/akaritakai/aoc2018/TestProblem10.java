package net.akaritakai.aoc2018;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestProblem10 extends BasePuzzleTest {

  private static final String EXAMPLE_INPUT = String.join("\n", List.of(
      "position=< 9,  1> velocity=< 0,  2>",
      "position=< 7,  0> velocity=<-1,  0>",
      "position=< 3, -2> velocity=<-1,  1>",
      "position=< 6, 10> velocity=<-2, -1>",
      "position=< 2, -4> velocity=< 2,  2>",
      "position=<-6, 10> velocity=< 2, -2>",
      "position=< 1,  8> velocity=< 1, -1>",
      "position=< 1,  7> velocity=< 1,  0>",
      "position=<-3, 11> velocity=< 1, -2>",
      "position=< 7,  6> velocity=<-1, -1>",
      "position=<-2,  3> velocity=< 1,  0>",
      "position=<-4,  3> velocity=< 2,  0>",
      "position=<10, -3> velocity=<-1,  1>",
      "position=< 5, 11> velocity=< 1, -2>",
      "position=< 4,  7> velocity=< 0, -1>",
      "position=< 8, -2> velocity=< 0,  1>",
      "position=<15,  0> velocity=<-2,  0>",
      "position=< 1,  6> velocity=< 1,  0>",
      "position=< 8,  9> velocity=< 0, -1>",
      "position=< 3,  3> velocity=<-1,  1>",
      "position=< 0,  5> velocity=< 0, -1>",
      "position=<-2,  2> velocity=< 2,  0>",
      "position=< 5, -2> velocity=< 1,  2>",
      "position=< 1,  4> velocity=< 2,  1>",
      "position=<-2,  7> velocity=< 2, -2>",
      "position=< 3,  6> velocity=<-1, -1>",
      "position=< 5,  0> velocity=< 1,  0>",
      "position=<-6,  0> velocity=< 2,  0>",
      "position=< 5,  9> velocity=< 1, -2>",
      "position=<14,  7> velocity=<-2,  0>",
      "position=<-3,  6> velocity=< 2, -1>"
  ));

  @Test
  public void testProblemPart1Example() {
    final var problem = new Problem10();
    final var expectedOutput = String.join("\n", List.of("",
        "............",
        ".#...#..###.",
        ".#...#...#..",
        ".#...#...#..",
        ".#####...#..",
        ".#...#...#..",
        ".#...#...#..",
        ".#...#...#..",
        ".#...#..###.",
        "............"
    ));
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals(expectedOutput, problem.solvePart1());
  }

  @Test
  public void testProblemPart1() throws Exception {
    final var problem = new Problem10();
    final var expectedOutput = String.join("\n", List.of("",
        "................................................................",
        ".#....#..#.......######....##....#....#..#####....####...######.",
        ".#....#..#............#...#..#...#...#...#....#..#....#.......#.",
        "..#..#...#............#..#....#..#..#....#....#..#............#.",
        "..#..#...#...........#...#....#..#.#.....#....#..#...........#..",
        "...##....#..........#....#....#..##......#####...#..........#...",
        "...##....#.........#.....######..##......#....#..#..###....#....",
        "..#..#...#........#......#....#..#.#.....#....#..#....#...#.....",
        "..#..#...#.......#.......#....#..#..#....#....#..#....#..#......",
        ".#....#..#.......#.......#....#..#...#...#....#..#...##..#......",
        ".#....#..######..######..#....#..#....#..#####....###.#..######.",
        "................................................................"
    ));
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals(expectedOutput, problem.solvePart1());
  }

  @Test
  public void testProblemPart2Example() {
    final var problem = new Problem10();
    problem.setPuzzleInput(EXAMPLE_INPUT);
    Assert.assertEquals("3", problem.solvePart2());
  }

  @Test
  public void testProblemPart2() throws Exception {
    final var problem = new Problem10();
    problem.setPuzzleInput(getStoredInput(problem.getDay()));
    Assert.assertEquals("10656", problem.solvePart2());
  }
}
