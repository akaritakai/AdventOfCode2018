package net.akaritakai.aoc2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;


public class Problem07 extends AbstractProblem {
  @Override
  public int getDay() {
    return 7;
  }

  @Override
  public String solvePart1() {
    final var requirements = getTaskRequirements();

    final var allTasks = requirements.keySet();
    final Set<String> completedTasks = new HashSet<>();

    final var output = new StringBuilder();
    while (completedTasks.size() < allTasks.size()) {
      final var availableTasks = availableTasks(requirements, completedTasks);
      // Get and complete the first available task
      final var task = availableTasks.get(0);
      output.append(task);
      completedTasks.add(task);
    }

    return output.toString();
  }

  @Override
  public String solvePart2() {
    final var numWorkers = 5;

    final var assignments = new String[numWorkers];
    final var timeLeft = new int[numWorkers];

    final var requirements = getTaskRequirements();
    final var allNodes = requirements.keySet();
    final Set<String> completedNodes = new HashSet<>();

    var elapsedTime = 0;
    while (completedNodes.size() < allNodes.size()) {
      // Tick down time left
      for (var i = 0; i < numWorkers; i++) {
        timeLeft[i]--;
      }

      // Check if workers are done and process their assignments
      for (var i = 0; i < numWorkers; i++) {
        if (timeLeft[i] <= 0) {
          if (assignments[i] != null) {
            // If we finished an assignment, mark it complete
            completedNodes.add(assignments[i]);
            assignments[i] = null;
          }
        }
      }

      // If all assignments are done, we are done
      if (completedNodes.size() == allNodes.size()) {
        break;
      }

      // Get available nodes
      final var available = availableTasks(requirements, completedNodes);

      // Tasks that are currently being worked on aren't available
      for (var i = 0; i < numWorkers; i++) {
        available.remove(assignments[i]);
      }

      // Assign workers to available tasks
      for (var task : available) {
        for (var i = 0; i < numWorkers; i++) {
          if (timeLeft[i] <= 0) {
            assignments[i] = task;
            timeLeft[i] = timeRequired(task);
            break;
          }
        }
      }

      elapsedTime++;
    }

    return String.valueOf(elapsedTime);
  }

  private List<String> availableTasks(@NotNull final Map<String, Set<String>> taskRequirements,
      @NotNull final Set<String> completedTasks) {
    final List<String> availableTasks = new ArrayList<>();
    taskRequirements.forEach((task, requirements) -> {
      // Already existing tasks don't need to be done again
      if (completedTasks.contains(task)) {
        return;
      }

      // Determine our current list of prerequisites needed by filtering out tasks already completed
      final var prerequisites = new HashSet<>(requirements);
      prerequisites.removeAll(completedTasks);

      // If we've met all the prerequisites, the task is available
      if (prerequisites.isEmpty()) {
        availableTasks.add(task);
      }
    });

    // Tasks should be completed in lexicographic order
    Collections.sort(availableTasks);

    return availableTasks;
  }

  /**
   * Returns the time required to do a task
   */
  private int timeRequired(@NotNull final String task) {
    return task.charAt(0) - 'A' + 61;
  }

  /**
   * Gets a list of tasks -> a Set of their prerequisites
   */
  private Map<String, Set<String>> getTaskRequirements() {
    final Map<String, Set<String>> map = new HashMap<>();
    getPuzzleInputLines().forEach(line -> {
          @RegExp final var regex = "^Step (\\S+) must be finished before step (\\S+) can begin.$";
          final var node = line.replaceAll(regex, "$2");
          final var requirement = line.replaceAll(regex, "$1");
          map.computeIfAbsent(node, s -> new HashSet<>());
          map.computeIfAbsent(requirement, s -> new HashSet<>());
          map.get(node).add(requirement);
        });
    return map;
  }
}
