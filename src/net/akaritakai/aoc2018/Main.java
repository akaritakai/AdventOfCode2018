package net.akaritakai.aoc2018;

import java.util.List;

public class Main {

    private static final List<AbstractProblem> PROBLEMS = List.of(
            new Problem1(),
            new Problem2(),
            new Problem3(),
            new Problem4(),
            new Problem5(),
            new Problem6(),
            new Problem7()
    );

    public static void main(String[] args) {
        PROBLEMS.forEach(problem -> {
            System.out.println("Day " + problem.getDay() + " Part 1: " + problem.solvePart1());
            System.out.println("Day " + problem.getDay() + " Part 2: " + problem.solvePart2());
        });
    }

}
