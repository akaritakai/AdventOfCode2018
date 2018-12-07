package net.akaritakai.aoc2018;

import java.util.List;

public class Main {

    private static final List<AbstractProblem> PROBLEMS = List.of(
            new Problem01(),
            new Problem02(),
            new Problem03(),
            new Problem04(),
            new Problem05(),
            new Problem06(),
            new Problem07()
    );

    public static void main(String[] args) {
        PROBLEMS.forEach(problem -> {
            final var day = String.format("%02d", problem.getDay());
            System.out.println("Day " + day + " Part 1: " + problem.solvePart1());
            System.out.println("Day " + day + " Part 2: " + problem.solvePart2());
        });
    }

}
