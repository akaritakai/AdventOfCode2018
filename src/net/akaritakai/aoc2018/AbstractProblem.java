package net.akaritakai.aoc2018;

public abstract class AbstractProblem {

    private volatile String _puzzleInput;

    /**
     * Gets the puzzle input for this day's problem
     * @return the puzzle input for this day's problem
     */
    protected String getPuzzleInput() {
        if (_puzzleInput == null) {
            _puzzleInput = PuzzleInputFetcher.getPuzzleInput(getDay());
        }
        return _puzzleInput;
    }

    /**
     * The day number of the puzzle
     * @return the day number of the puzzle
     */
    public abstract int getDay();

    /**
     * Returns the solution to part 1 of the problem
     * @return the solution to part 1 of the problem
     */
    public abstract String solvePart1();

    /**
     * Returns the solution to part 2 of the problem
     * @return the solution to part 2 of the problem
     */
    public abstract String solvePart2();
}