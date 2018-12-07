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


public class Problem7 extends AbstractProblem {
  @Override
  public int getDay() {
    return 7;
  }

  @Override
  public String solvePart1() {
    var requirements = getTaskRequirements();

    var allTasks = getAllTasks(requirements);
    Set<String> completedTasks = new HashSet<>();

    var sb = new StringBuilder();
    while (completedTasks.size() < allTasks.size()) {
      var availableTasks = availableTasks(requirements, completedTasks);
      // Get and complete the first available task
      var task = availableTasks.get(0);
      sb.append(task);
      completedTasks.add(task);
    }

    return sb.toString();
  }

  @Override
  public String solvePart2() {
    final var numWorkers = 5;

    final var assignments = new String[numWorkers];
    final var timeLeft = new int[numWorkers];

    final var requirements = getTaskRequirements();
    final var allNodes = getAllTasks(requirements);
    final Set<String> completedNodes = new HashSet<>();

    var elapsedTime = 0;
    while (completedNodes.size() < allNodes.size()) {
      // Tick down time left
      for (var i = 0; i < 5; i++) {
        timeLeft[i]--;
      }

      // Check if workers are done and process their assignments
      for (int i = 0; i < 5; i++) {
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
      var available = availableTasks(requirements, completedNodes);

      // Tasks that are currently being worked on aren't available
      for (int i = 0; i < 5; i++) {
        available.remove(assignments[i]);
      }

      // Assign workers to available tasks
      for (String task : available) {
        for (int i = 0; i < 5; i++) {
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

  private List<String> availableTasks(Map<String, Set<String>> taskRequirements, Set<String> completedTasks) {
    final List<String> availableTasks = new ArrayList<>();
    taskRequirements.forEach((task, requirements) -> {
      // Already existing tasks don't need to be done again
      if (completedTasks.contains(task)) {
        return;
      }

      // Filter out already completed tasks from the requirements list
      var copy = new HashSet<>(requirements);
      copy.removeAll(completedTasks);

      // If we've met all the prerequisites, the task is available
      if (copy.isEmpty()) {
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
   * Gets the set of all tasks we know about
   */
  private Set<String> getAllTasks(@NotNull final Map<String, Set<String>> requirements) {
    final Set<String> allNodes = new HashSet<>();
    requirements.forEach((node, reqs) -> {
      allNodes.add(node);
      allNodes.addAll(reqs);
    });
    return allNodes;
  }

  /**
   * Gets a list of tasks -> a Set of their prerequisites
   */
  private Map<String, Set<String>> getTaskRequirements() {
    final Map<String, Set<String>> map = new HashMap<>();
    getPuzzleInputLines().forEach(line -> {
          @RegExp final var regex = "^Step (\\S+) must be finished before step (\\S+) can begin.";
          var node = line.replaceAll(regex, "$2");
          var nodeRequirement = line.replaceAll(regex, "$1");
          map.computeIfAbsent(node, s -> new HashSet<>());
          map.computeIfAbsent(nodeRequirement, s -> new HashSet<>());
          map.get(node).add(nodeRequirement);
        });
    return map;
  }
}
