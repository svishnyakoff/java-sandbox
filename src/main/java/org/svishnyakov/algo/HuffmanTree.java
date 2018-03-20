package org.svishnyakov.algo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Week 3 - greedy algorithms.
 *
 * Construction of the Huffman tree
 */
public class HuffmanTree {

    public static void main(String[] args) throws Exception {

        int[] weights = Files.loadArray("/algo/huffman.txt");

        List<Node> nodes = Arrays.stream(weights).boxed()
                .skip(1)
                .map(Node::new)
                .sorted()
                .collect(Collectors.toList());

        Node root = buildHuffmanTree(nodes);

        System.out.println("Max code length: " + findMaxCodeLength(root, 0));
        System.out.println("Min code length: " + findMinCodeLength(root, 0));
    }

    private static int findMaxCodeLength(Node root, int depth) {
        if (root == null) {
            return depth - 1;
        }

        if (root.left == null && root.right == null) {
            return depth;
        }

        return Math.max(findMaxCodeLength(root.left, depth + 1), findMaxCodeLength(root.right, depth + 1));
    }

    private static int findMinCodeLength(Node root, int depth) {
        if (root == null) {
            return depth - 1;
        }

        return Math.min(findMinCodeLength(root.left, depth + 1), findMaxCodeLength(root.right, depth + 1));
    }

   static Node buildHuffmanTree(List<Node> nodes) {
        Queue<Node> merged = new LinkedList<>();
        Queue<Node> toVisit = new LinkedList<>();

        toVisit.addAll(nodes);

        while(merged.size() > 1 || toVisit.size() > 1) {
            Node a = getMin(merged, toVisit);
            Node b = getMin(merged, toVisit);
            merged.add(merge(a, b));
        }

        return merged.peek();
    }

    private static Node getMin(Queue<Node> a, Queue<Node> b) {
        if (a.isEmpty()) {
            return b.poll();
        }

        if (b.isEmpty()) {
            return a.poll();
        }

        return a.peek().weight > b.peek().weight ? b.poll() : a.poll();
    }

    private static Node merge(Node a, Node b) {
        return new Node(a, b, a.weight + b.weight);
    }

    static class Node implements Comparable<Node> {
        final Node left;
        final Node right;
        final int weight;

        Node(int weight) {
            this.weight = weight;
            this.left = null;
            this.right = null;
        }

        Node(Node left, Node right, int weight) {
            this.left = left;
            this.right = right;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }
}
