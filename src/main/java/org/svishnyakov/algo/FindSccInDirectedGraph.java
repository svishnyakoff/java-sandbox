package org.svishnyakov.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Kosaraju’s algorithm implementation that is using two way DFS
 * https://www.geeksforgeeks.org/strongly-connected-components/
 */
public class FindSccInDirectedGraph {

    static Deque<Integer> traversedVertices = new LinkedList<>();

    public static void main(String[] args) throws Exception {
        List<int[]> rawGraph = Files.loadGraph("/algo/scc.txt");

        log("File is loaded");

        Graph graph = new Graph();
        for (int[] edge : rawGraph) {
            graph.add(edge[0], edge[1]);
        }

        log("Graph is populated with vertices");

        Graph reversedGraph = reverse(graph);
        log("Reversed graph is created");

        dfsLoop(reversedGraph);

        Deque<Integer> verticesToTraverse = new LinkedList<>();
        verticesToTraverse.addAll(traversedVertices);

        log("First traversal finished");

        traversedVertices.clear();

        List<Integer> sccSizes = dfsLoopAndCountScc(graph, verticesToTraverse);

        List<Integer> topBiggestScc = sccSizes.stream()
                .sorted(Comparator.reverseOrder())
                .limit(5)
                .collect(Collectors.toList());

        System.out.println(topBiggestScc);
    }

    private static void log(String str) {
        System.out.println(str);
    }

    private static List<Integer> dfsLoopAndCountScc(Graph graph, Deque<Integer> vertices) {

        List<Integer> sizeOfSccs = new LinkedList<>();

        for (Integer vertex : vertices) {
            if (!graph.isVisited(vertex)) {
                dfs(graph, vertex);
                sizeOfSccs.add(traversedVertices.size());
                traversedVertices.clear();
            }
        }

        return sizeOfSccs;
    }

    static void  dfsLoop(Graph graph) {
        for (Integer vertex : graph.allVertices()) {
            if (!graph.isVisited(vertex)) {
                dfs(graph, vertex);
            }
        }
    }

    /**
     * DFS postorder traversal of the graph without recursion
     *
     * https://en.wikipedia.org/wiki/Tree_traversal
     */
    private static void dfs(Graph graph, Integer vertex) {

        Deque<Integer> stack = new LinkedList<>();
        Deque<Integer> visited = new LinkedList<>();

        stack.push(vertex);
        graph.visit(vertex);

        while (!stack.isEmpty()) {
            Integer currentVertex = stack.peek();
            boolean allChildHasBeenVisited = true;

            for (Integer sibling : graph.getSiblings(currentVertex)) {
                if (!graph.isVisited(sibling)) {
                    graph.visit(sibling);
                    stack.push(sibling);
                    allChildHasBeenVisited = false;
                }
            }

            if (allChildHasBeenVisited) {
                logVertex(stack.peek());
                visited.push(stack.poll());
            }
        }

        while (!visited.isEmpty()) {
            Integer currentVertex = visited.pollLast();
            traversedVertices.push(currentVertex);
        }
    }

    private static void logVertex(Integer vertex) {
//        System.out.println(String.format("Visited vertex: %s", vertex));
    }

    private static Graph reverse(Graph graph) {
        Graph reversedGraph = new Graph();

        graph.siblings.forEach((vertex, siblings) -> {
            for (Integer sibling : siblings) {
                reversedGraph.add(sibling, vertex);
            }
        });

        return reversedGraph;
    }

    private static class Graph {
        private Map<Integer, List<Integer>> siblings = new HashMap<>(1000000);
        private Set<Integer> visited = new HashSet<>();

        List<Integer> getSiblings(Integer vertex) {
            return siblings.getOrDefault(vertex, Collections.emptyList());
        }

        void add(Integer vertex, Integer sibling) {
            List<Integer> vertices = siblings.computeIfAbsent(vertex, Graph::newList);
            vertices.add(sibling);

            if (!siblings.containsKey(sibling)) {
                siblings.put(sibling, newList(1));
            }
        }

        private static List<Integer> newList(int k) {
            return new ArrayList<>();
        }

        Set<Integer> allVertices() {
            return siblings.keySet();
        }

        void visit(Integer vertex) {
            visited.add(vertex);
        }

        boolean isVisited(Integer vertex) {
            return visited.contains(vertex);
        }
    }
}
