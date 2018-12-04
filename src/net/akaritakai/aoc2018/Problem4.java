package net.akaritakai.aoc2018;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.intellij.lang.annotations.RegExp;


public class Problem4 extends AbstractProblem {
  @Override
  public int getDay() {
    return 4;
  }

  @Override
  public String solvePart1() {
    var schedule = processInput();

    // Strategy #1: Sleepiest guard is the one who sleeps the most over all the minutes
    var sleepiestGuard = schedule.entrySet()
        .stream()
        .max(Comparator.comparingLong(e -> e.getValue().values().stream().mapToInt(i -> i).sum()))
        .map(Map.Entry::getKey)
        .orElse(-1);
    var sleepiestMinute = schedule.get(sleepiestGuard)
        .entrySet()
        .stream()
        .max(Comparator.comparingLong(Map.Entry::getValue))
        .map(Map.Entry::getKey)
        .orElse(-1);

    var checksum = sleepiestGuard * sleepiestMinute;
    return String.valueOf(checksum);
  }

  @Override
  public String solvePart2() {
    var schedule = processInput();

    // Strategy #2: Sleepiest guard is the one who sleeps the most in any minute
    var sleepiestGuard = schedule.entrySet()
        .stream()
        .max(Comparator.comparingInt(e -> e.getValue().values().stream().max(Integer::compare).orElse(-1)))
        .map(Map.Entry::getKey)
        .orElse(-1);
    var sleepiestMinute = schedule.get(sleepiestGuard)
        .entrySet()
        .stream()
        .max(Comparator.comparingInt(Map.Entry::getValue))
        .map(Map.Entry::getKey)
        .orElse(-1);

    var checksum = sleepiestGuard * sleepiestMinute;
    return String.valueOf(checksum);
  }

  /**
   * Creates and returns the guard schedule:
   *   a map of: guard -> minute -> # of times fallen asleep
   */
  private Map<Integer, Map<Integer, Integer>>  processInput() {
    final Map<Integer, Map<Integer, Integer>> schedule =  new HashMap<>();
    final var lines = getPuzzleInput().lines().sorted().collect(Collectors.toList());

    int guard = -1;
    int startedSleeping = -1;
    for (final String line : lines) {
      if (line.contains("begins shift")) {
        @RegExp final var regex = "^\\[\\d+-\\d+-\\d+ \\d+:\\d+] Guard #(\\d+) begins shift$";
        guard = Integer.parseInt(line.replaceAll(regex, "$1"));
      }
      if (line.contains("falls asleep")) {
        @RegExp final var regex = "^\\[\\d+-\\d+-\\d+ \\d+:(\\d+)] falls asleep";
        startedSleeping = Integer.parseInt(line.replaceAll(regex, "$1"));
      }
      if (line.contains("wakes up")) {
        @RegExp final var regex = "^\\[\\d+-\\d+-\\d+ \\d+:(\\d+)] wakes up";
        final var wokeUp = Integer.parseInt(line.replaceAll(regex, "$1"));
        for (var minute = startedSleeping; minute < wokeUp; minute++) {
          schedule.computeIfAbsent(guard, s -> new HashMap<>())
              .compute(minute, (k, v) -> (v == null) ? 1 : v + 1);
        }
      }
    }

    return schedule;
  }



}
