package org.svishnyakov.hacherrank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * Find a shortest path using Breadth First Search
 * https://www.hackerrank.com/challenges/ctci-bfs-shortest-reach/problem
 */
public class BFS {

    static class Graph {
        Map<Integer, List<Integer>> adjacentMap = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        Graph(int numberOfNodes) {
            for (int i = 1; i <= numberOfNodes; i++) {
                adjacentMap.put(i, new ArrayList<>());
            }
        }

        void add(Integer first, Integer second) {
            adjacentMap.get(first).add(second);
            adjacentMap.get(second).add(first);
        }

        List<Integer> getNeighbors(Integer node) {
            return adjacentMap.get(node);
        }

        Set<Integer> allNodes() {
            return adjacentMap.keySet();
        }

        void visit(Integer node) {
            visited.add(node);
        }

        boolean isVisited(Integer node) {
            return visited.contains(node);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int queries = scanner.nextInt();

        for (int i = 0; i < queries; i++) {
            int nodes = scanner.nextInt();
            Graph graph = new Graph(nodes);

            int edges = scanner.nextInt();

            for (int j = 0; j < edges; j++) {
                graph.add(scanner.nextInt(), scanner.nextInt());
            }

            int startNode = scanner.nextInt();

            Map<Integer, Integer> distances = lookUpDistancesFrom(graph, startNode);

            for (Map.Entry<Integer, Integer> entry : distances.entrySet()) {
                if (entry.getKey().equals(startNode)) {
                    continue;
                }

                Integer value = entry.getValue() == -6 ? -1 : entry.getValue();
                System.out.print(value);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    static Map<Integer, Integer> lookUpDistancesFrom(Graph graph, Integer startNode) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> distances = new TreeMap<>();

        queue.add(startNode);

        for (Integer node : graph.allNodes()) {
            distances.put(node, -6);
        }

        distances.put(startNode, 0);


        while (!queue.isEmpty()) {
            Integer currentNode = queue.poll();
            Integer distanceToHere = distances.get(currentNode);

            for (Integer neighbor : graph.getNeighbors(currentNode)) {
                if (!graph.isVisited(neighbor)) {
                    graph.visit(neighbor);
                    queue.add(neighbor);
                    distances.put(neighbor, distanceToHere + 6);
                }
            }
        }

        return distances;
    }
}
