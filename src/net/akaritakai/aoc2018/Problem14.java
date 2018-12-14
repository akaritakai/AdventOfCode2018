package net.akaritakai.aoc2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;


public class Problem14 extends AbstractProblem {
  @Override
  public int getDay() {
    return 14;
  }

  @Override
  public String solvePart1() {
    initRecipes();
    final var input = Integer.parseInt(getPuzzleInput().trim());

    while (recipes.size() < input + 10) {
      iterate();
    }

    final var result = new StringBuilder();
    for (int i = input; i < recipes.size(); i++) {
      result.append(recipes.get(i));
    }

    return result.toString();
  }

  @Override
  public String solvePart2() {
    initRecipes();
    final var input = getPuzzleInput().trim().chars().map(i -> i - '0').boxed().collect(Collectors.toList());

    while (true) {
      // Add more recipes
      final var prevSize = recipes.size();
      iterate();

      // If we added 1 or 2 recipes, check the last (n-input.size, n) characters to see if they match our input
      if (recipes.size() < input.size()) {
        continue;
      }
      if (findMatch(input, recipes.size() - input.size())) {
        return String.valueOf(recipes.size() - input.size());
      }

      // If we added 2 recipes, check the last (n-input.size-1, n-1) characters to see if they match our input
      if (recipes.size() < input.size() + 1) {
        continue;
      }
      if (recipes.size() == prevSize + 2) {
        if (findMatch(input, recipes.size() - input.size() - 1)) {
          return String.valueOf(recipes.size() - input.size() - 1);
        }
      }
    }
  }

  /**
   * The recipe position of the first elf
   */
  private int elfPos1;

  /**
   * The recipe position of the second elf
   */
  private int elfPos2;

  /**
   * The list of recipes
   */
  private List<Integer> recipes;

  /**
   * Initializes the list of recipes
   */
  private void initRecipes() {
    elfPos1 = 0;
    elfPos2 = 1;
    recipes = new ArrayList<>();
    recipes.add(3);
    recipes.add(7);
  }

  /**
   * Adds recipes based on our rules
   */
  private void iterate() {
    final var elf1 = recipes.get(elfPos1);
    final var elf2 = recipes.get(elfPos2);
    final var sum = elf1 + elf2;
    if (sum >= 10) {
      recipes.add(1);
    }
    recipes.add(sum % 10);
    elfPos1 = (elfPos1 + elf1 + 1) % recipes.size();
    elfPos2 = (elfPos2 + elf2 + 1) % recipes.size();
  }

  /**
   * Checks if the provided list of numbers are present in our recipes at the given starting position
   */
  private boolean findMatch(@NotNull final List<Integer> list, final int startPos) {
    var matches = true;
    for (var i = 0; i < list.size(); i++) {
      if (!Objects.equals(list.get(i), recipes.get(startPos + i))) {
        matches = false;
        break;
      }
    }
    return matches;
  }

}
