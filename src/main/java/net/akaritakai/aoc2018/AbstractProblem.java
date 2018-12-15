package net.akaritakai.aoc2018;

abstract class AbstractProblem {

  private volatile String _puzzleInput;

  /**
   * Gets the puzzle input for this day's problem
   * @return the puzzle input for this day's problem
   */
  String getPuzzleInput() {
    if (_puzzleInput != null) {
      return _puzzleInput;
    }
    return PuzzleInputFetcher.getPuzzleInput(getDay());
  }

  /**
   * Sets the puzzle input for this day's problem. Used for testing.
   * @param input the input to set
   */
  public void setPuzzleInput(final String input) {
    _puzzleInput = input;
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