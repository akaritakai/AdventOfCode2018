package net.akaritakai.aoc2018;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class Problem5 extends AbstractProblem {
  @Override
  public int getDay() {
    return 5;
  }

  @Override
  public String solvePart1() {
    return String.valueOf(reduce(getPolymer()).length());
  }

  @Override
  public String solvePart2() {
    final var polymerLength = IntStream.rangeClosed('a', 'z')
        .parallel()
        .mapToObj(c -> removeType(getPolymer(), (char) c))
        .mapToInt(s -> reduce(s).length())
        .min()
        .orElse(-1);
    return String.valueOf(polymerLength);
  }

  private String reduce(String polymer) {
    var prev = polymer;
    while (true) {
      polymer = reduceOnce(polymer);
      if (prev.equals(polymer)) {
        break;
      } else {
        prev = polymer;
      }
    }
    return polymer;
  }

  private String reduceOnce(String polymer) {
    for (var pair : REACTION_PAIR) {
      polymer = polymer.replace(pair, "");
    }
    return polymer;
  }

  private String removeType(final String polymer, final char type) {
    return polymer
        .replace("" + Character.toLowerCase(type), "")
        .replace("" + Character.toUpperCase(type), "");
  }

  private static final List<String> REACTION_PAIR = new ArrayList<>();
  static {
    for (var lower = 'a'; lower <= 'z'; lower++) {
      var upper = Character.toUpperCase(lower);
      REACTION_PAIR.add("" + lower + upper);
      REACTION_PAIR.add("" + upper + lower);
    }
  }

  private String getPolymer() {
    return getPuzzleInput().trim();
  }

}
