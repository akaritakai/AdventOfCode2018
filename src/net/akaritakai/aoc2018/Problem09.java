package net.akaritakai.aoc2018;

import org.intellij.lang.annotations.RegExp;

import java.util.ArrayDeque;
import java.util.Arrays;

public class Problem09 extends AbstractProblem {
    @Override
    public int getDay() {
        return 9;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getScore(getNumPlayers(), getLastMarble()));
    }


    @Override
    public String solvePart2() {
        return String.valueOf(getScore(getNumPlayers(), getLastMarble() * 100));
    }

    /**
     * Play the game with the specified number players and the specified value of the last marble
     * @return the winner's score
     */
    private long getScore(int numPlayers, int lastMarble) {
        final var scores = new long[numPlayers];
        final var marbles = new Marbles();
        marbles.addFirst(0);
        for (var marble = 1; marble <= lastMarble; marble++) {
            if (marble % 23 == 0) {
                marbles.rotate(-7);
                scores[marble % numPlayers] += marble + marbles.pop();
            } else {
                marbles.rotate(2);
                marbles.addLast(marble);
            }
        }
        return Arrays.stream(scores).max().orElseThrow();
    }

    /**
     * A collection which emulates the current game of Marbles.
     */
    private static class Marbles extends ArrayDeque<Integer> {
        /**
         * Rotate the deque n steps to the right. If n is negative, rotate to the left.
         */
        void rotate(final int n) {
            if (n > 0) {
                for (var i = 0 ; i < n; i++) {
                    addFirst(removeLast());
                }
            } else if (n < 0) {
                for (var i = 0; i < Math.abs(n) - 1; i++) {
                    addLast(removeFirst());
                }
            }
        }
    }

    private int getNumPlayers() {
        @RegExp final var regex = "^(\\d+) players; last marble is worth \\d+ points$";
        return Integer.parseInt(getPuzzleInput().replaceAll(regex, "$1"));
    }

    private int getLastMarble() {
        @RegExp final var regex = "^\\d+ players; last marble is worth (\\d+) points$";
        return Integer.parseInt(getPuzzleInput().replaceAll(regex, "$1"));
    }
}
