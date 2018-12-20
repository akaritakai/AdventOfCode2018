package net.akaritakai.aoc2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;


@SuppressWarnings("Duplicates")
public class Problem19 extends AbstractProblem {
  @Override
  public int getDay() {
    return 19;
  }

  @Override
  public String solvePart1() {
    processInput();
    while (notHalted()) {
      run();
    }
    return String.valueOf(registers[0]);
  }

  @Override
  public String solvePart2() {
    processInput();
    registers[0] = 1;

    /*
     * This algorithm won't work on any arbitrary input, but should work on Advent of Code inputs.
     *
     * It seems from the subreddit that everyone's inputs end up in the following program:
     * 1. A large number is placed in a register
     * 2. The program finds factors of this number greater than 1
     * 3. When a new factor is found, it is added with register 0
     * 4. The program halts when register 0 contains the sum of factors of the large number
     *
     * This solution assumes this is the case, and does not validate it.
     */

    final var repeated = (Predicate<Map<Integer, Integer>>) freq -> freq.values().stream().anyMatch(i -> i > 1);
    final var freq = new HashMap<Integer, Integer>();
    final var accumulate = (Function<Integer, Integer>) ip -> freq.put(ip, freq.getOrDefault(ip, 0) + 1);

    while (notHalted()) {
      accumulate.apply(registers[ip]);
      run();
      if (repeated.test(freq)) {
        break; // Break if we've repeated any instruction
      }
    }

    final var unmodified = registers.clone(); // Unmodified register values
    freq.clear();
    while (notHalted()) {
      accumulate.apply(registers[ip]);
      run();
      for (var i = 0; i < registers.length; i++) {
        if (unmodified[i] != registers[i]) {
          unmodified[i] = 0; // If the register was modified, remove it from our list of unmodified registers
        }
      }
      if (repeated.test(freq)) {
        break; // Break if we've repeated any instruction
      }
    }

    final var target = Arrays.stream(unmodified).max().orElseThrow();
    final var factors = IntStream.rangeClosed(1, target).filter(i -> target % i == 0);

    return String.valueOf(factors.sum());
  }

  /**
   * Performs one operation of the machine if it has not already halted
   */
  private void run() {
    if (notHalted()) {
      operate(registers, instructions.get(registers[ip]));
      registers[ip]++;
    }
  }

  /**
   * Detects if the machine has not halted yet
   */
  private boolean notHalted() {
    return registers[ip] >= 0 && registers[ip] < instructions.size();
  }

  private int ip;
  private int[] registers;
  private List<Instruction> instructions;

  private void processInput() {
    final var lines = getPuzzleInput().lines().map(String::trim).filter(line -> !line.isEmpty()).collect(Collectors.toList());
    registers = new int[6];
    instructions = new ArrayList<>();
    for (String line : lines) {
      if (line.startsWith("#ip ")) {
        ip = Integer.parseInt(line.replace("#ip ", ""));
      } else {
        instructions.add(new Instruction(line));
      }
    }
  }

  /**
   * Describes an instruction which has an opcode, 2 inputs (a and b), and 1 output (c)
   */
  static class Instruction {
    final Operation operation;
    final int a;
    final int b;
    final int c;

    Instruction(@NotNull final String instruction) {
      final var tokens = instruction.split("\\s+");
      assert(tokens.length == 4);
      operation = Operation.valueOf(tokens[0].toUpperCase());
      a = Integer.parseInt(tokens[1]);
      b = Integer.parseInt(tokens[2]);
      c = Integer.parseInt(tokens[3]);
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
  private void operate(@NotNull final int[] registers, @NotNull final Instruction instruction) {
    final var operation = instruction.operation;
    final var a = instruction.a;
    final var b = instruction.b;
    final var c = instruction.c;
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
