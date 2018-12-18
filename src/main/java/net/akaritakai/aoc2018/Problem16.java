package net.akaritakai.aoc2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;


@SuppressWarnings("Duplicates")
public class Problem16 extends AbstractProblem {
  @Override
  public int getDay() {
    return 16;
  }

  @Override
  public String solvePart1() {
    final var result = getSamples().stream().map(sample ->
        Arrays.stream(Operation.values()).map(operation -> {
          final var registers = Arrays.copyOf(sample.before, sample.before.length);
          operate(registers, operation, sample.instruction.a, sample.instruction.b, sample.instruction.c);
          return Arrays.equals(registers, sample.after);
        }).filter(a -> a).count())
        .filter(matches -> matches >= 3)
        .count();

    return String.valueOf(result);
  }

  @Override
  public String solvePart2() {
    // Create a map of operations -> possible opcodes
    final var possibleOpcodes = new HashMap<Operation, Set<Integer>>();
    Arrays.stream(Operation.values()).forEach(operation -> {
      // Every operation could have any opcode
      final var set = IntStream.range(0, Operation.values().length).boxed().collect(Collectors.toSet());
      possibleOpcodes.put(operation, set);
    });

    // For each sample, test each operation to see if that sample works with that operation
    getSamples().forEach(sample -> Arrays.stream(Operation.values()).forEach(operation -> {
      final var registers = Arrays.copyOf(sample.before, sample.before.length);
      operate(registers, operation, sample.instruction.a, sample.instruction.b, sample.instruction.c);
      if (!Arrays.equals(registers, sample.after)) {
        // If this operation couldn't have worked, remove it from our list of possible opcodes
        possibleOpcodes.get(operation).remove(sample.instruction.opcode);
      }
    }));

    // Do logical reduction
    while (true) {
      final var reductionNeeded = possibleOpcodes.values().stream().map(Set::size).anyMatch(size -> size > 1);
      if (!reductionNeeded) {
        break;
      }
      final var uniqueOpcodes = possibleOpcodes.values()
          .stream()
          .filter(set -> set.size() == 1)
          .flatMap(Collection::stream)
          .collect(Collectors.toSet());
      possibleOpcodes.values()
          .stream()
          .filter(list -> list.size() > 1)
          .forEach(list -> list.removeAll(uniqueOpcodes));
    }

    // Invert the mapping of possible opcodes -> the list of real opcodes
    final var opcodes = new HashMap<Integer, Operation>();
    possibleOpcodes.forEach((operation, codes) -> {
      assert (codes.size() == 1);
      final var opcode = codes.stream().findAny().orElseThrow();
      opcodes.put(opcode, operation);
    });

    // Run the program
    final var registers = new int[]{0, 0, 0, 0};
    getInstructions().forEach(instruction ->
        operate(registers, opcodes.get(instruction.opcode), instruction.a, instruction.b, instruction.c));

    return String.valueOf(registers[0]);
  }

  /**
   * Extracts our black box testing samples from the puzzle input
   */
  private List<Sample> getSamples() {
    final var lines = getPuzzleInput().lines().collect(Collectors.toList());

    // Process samples
    final var samples = new ArrayList<Sample>();
    for (var i = 0; i <= lines.size() - 3;) {
      if (lines.get(i).isBlank()) {
        i++;
        continue;
      }
      final var line1 = lines.get(i);
      final var line2 = lines.get(i + 1);
      final var line3 = lines.get(i + 2);
      if (line1.startsWith("Before:") && line3.startsWith("After:")) {
        // Process before line
        final var before = Arrays.stream(line1.replaceAll("\\D", " ").split("\\s+"))
            .filter(token -> !token.isEmpty())
            .mapToInt(Integer::parseInt)
            .toArray();
        // Process instruction line
        final var instructions = Arrays.stream(line2.replaceAll("\\D", " ").split("\\s+"))
            .filter(token -> !token.isEmpty())
            .mapToInt(Integer::parseInt)
            .toArray();
        // Process after line
        final var after = Arrays.stream(line3.replaceAll("\\D", " ").split("\\s+"))
            .filter(token -> !token.isEmpty())
            .mapToInt(Integer::parseInt)
            .toArray();
        // Add the sample
        samples.add(new Sample(new Instruction(instructions), before, after));
        i += 3;
      } else {
        // We finished processing the samples
        break;
      }
    }

    return samples;
  }

  /**
   * Get our program instructions from the puzzle input
   */
  private List<Instruction> getInstructions() {
    final var lines = getPuzzleInput().lines().collect(Collectors.toList());

    // Skip over the list of samples
    var i = 0;
    for (; i <= lines.size() - 3;) {
      if (lines.get(i).isBlank()) {
        i++;
        continue;
      }
      if (lines.get(i).startsWith("Before:") && lines.get(i + 2).startsWith("After:")) {
        i += 3;
      } else {
        break;
      }
    }

    // Take the remaining lines and process them into instructions
    return IntStream.range(i, lines.size())
        .mapToObj(lines::get)
        .filter(line -> !line.isEmpty())
        .map(line -> {
          final var instructions = Arrays.stream(line.replaceAll("\\D", " ").split("\\s+"))
              .filter(token -> !token.isEmpty())
              .mapToInt(Integer::parseInt)
              .toArray();
          return new Instruction(instructions);
        })
        .collect(Collectors.toList());
  }

  /**
   * Describes a Sample which includes an instruction that transforms the registers from the state before -> after
   */
  static class Sample {
    final Instruction instruction;
    final int[] before;
    final int[] after;

    Sample(@NotNull Instruction instruction, @NotNull int[] before, @NotNull int[] after) {
      this.instruction = instruction;
      this.before = before;
      this.after = after;
    }
  }

  /**
   * Describes an instruction which has an opcode, 2 inputs (a and b), and 1 output (c)
   */
  static class Instruction {
    final int opcode;
    final int a;
    final int b;
    final int c;

    Instruction(@NotNull int[] codes) {
      assert(codes.length == 4);
      this.opcode = codes[0];
      this.a = codes[1];
      this.b = codes[2];
      this.c = codes[3];
    }
  }

  /**
   * Describes the list of operations
   */
  private enum Operation {
    // Addition
    ADDR,
    ADDI,
    // Multiplication
    MULR,
    MULI,
    // Bitwise AND
    BANR,
    BANI,
    // Bitwise OR
    BORR,
    BORI,
    // Assignment
    SETR,
    SETI,
    // Greater-Than Testing
    GTIR,
    GTRI,
    GTRR,
    // Equality Testing
    EQIR,
    EQRI,
    EQRR
  }

  /**
   * Performs an operation given the current state of registers, the operation to be done, and the values a, b, and c
   * provided to the operation.
   */
  private void operate(@NotNull int[] registers, @NotNull Operation operation, int a, int b, int c) {
    switch (operation) {
      case ADDR:
        registers[c] = registers[a] + registers[b];
        break;
      case ADDI:
        registers[c] = registers[a] + b;
        break;
      case MULR:
        registers[c] = registers[a] * registers[b];
        break;
      case MULI:
        registers[c] = registers[a] * b;
        break;
      case BANR:
        registers[c] = registers[a] & registers[b];
        break;
      case BANI:
        registers[c] = registers[a] & b;
        break;
      case BORR:
        registers[c] = registers[a] | registers[b];
        break;
      case BORI:
        registers[c] = registers[a] | b;
        break;
      case SETR:
        registers[c] = registers[a];
        break;
      case SETI:
        registers[c] = a;
        break;
      case GTIR:
        registers[c] = a > registers[b] ? 1 : 0;
        break;
      case GTRI:
        registers[c] = registers[a] > b ? 1 : 0;
        break;
      case GTRR:
        registers[c] = registers[a] > registers[b] ? 1 : 0;
        break;
      case EQIR:
        registers[c] = a == registers[b] ? 1 : 0;
        break;
      case EQRI:
        registers[c] = registers[a] == b ? 1 : 0;
        break;
      case EQRR:
        registers[c] = registers[a] == registers[b] ? 1 : 0;
        break;
    }
  }


}
