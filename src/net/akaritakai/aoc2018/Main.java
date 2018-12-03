package net.akaritakai.aoc2018;

import java.util.List;

public class Main {

    private static final List<AbstractProblem> PROBLEMS = List.of(
            new Problem1()
    );

    public static void main(String[] args) {
        PROBLEMS.forEach(problem -> {
            System.out.println("Day " + problem.getDay() + " Part 1: " + problem.solvePart1());
            System.out.println("Day " + problem.getDay() + " Part 2: " + problem.solvePart2());
        });
    }

}
