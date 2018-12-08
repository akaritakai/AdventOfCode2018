package net.akaritakai.aoc2018;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Problem08 extends AbstractProblem {
    @Override
    public int getDay() {
        return 8;
    }

    @Override
    public String solvePart1() {
        processInput(getInput(), null, 0);
        return String.valueOf(sumAllMetadata(root));
    }

    @Override
    public String solvePart2() {
        processInput(getInput(), null, 0);
        return String.valueOf(nodeValue(root));
    }

    /**
     * Sums all of the metadata from this node and all of its children
     */
    private int sumAllMetadata(final Node node) {
        var sum = node.metadataSum();
        for (final Node child : node.children) {
            sum += sumAllMetadata(child);
        }
        return sum;
    }

    /**
     * Gets the provided Node's value
     */
    private int nodeValue(@NotNull final Node node) {
        if (node.children.isEmpty()) {
            return node.metadataSum();
        }
        return node.metadata.stream()
                .filter(metadata -> metadata > 0 && metadata <= node.children.size())
                .mapToInt(metadata -> nodeValue(node.children.get(metadata - 1)))
                .sum();
    }

    /**
     * The root Node of our tree
     */
    private Node root;

    /**
     * Processes the input, returning the stream position of the next Node to process at each iteration
     */
    private int processInput(@NotNull final List<Integer> input, @Nullable final Node parent, int position) {
        final var node = new Node();
        if (parent == null) {
            root = node;
        } else {
            parent.children.add(node);
        }
        final var numChildren = input.get(position++);
        final var numMetadataEntries = input.get(position++);
        for (var i = 0; i < numChildren; i++) {
            position = processInput(input, node, position);
        }
        for (var i = 0; i < numMetadataEntries; i++) {
            node.metadata.add(input.get(position++));
        }
        return position;
    }

    /**
     * Gets the input as a list of numbers
     */
    private List<Integer> getInput() {
        return Arrays.stream(getPuzzleInput().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    /**
     * A Node class which has children and metadata
     */
    static class Node {
        final List<Node> children = new ArrayList<>();
        final List<Integer> metadata = new ArrayList<>();

        /**
         * Returns the sum of this Node's metadata values
         */
        int metadataSum() {
            return metadata.stream().reduce((a, b) -> a + b).orElse(0);
        }
    }

}
