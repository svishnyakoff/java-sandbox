package org.svishnyakov.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Week 1: Greedy algorithms
 *
 * Implementation of Prime's algorithm for Minimum Spanning Trees
 */
public class MstPrime {

    public static void main(String[] args) throws Exception {
        Graph graph = loadGraph("/algo/mst.txt");

        long mstMinCost = mst(graph);

        info("Min cost: %s", mstMinCost);
    }

    private static long mst(Graph graph) {
        PriorityQueue<Vertex> heap = new PriorityQueue<>(graph.vertices.values());
        Set<Integer> visited = new HashSet<>();
        long mstMinCost = 0;

        Vertex v = heap.poll();
        v.setMinCost(0);

        do {
            debug("Visit node [%s] with cost [%s] ", v.id, v.minCost);
            visited.add(v.id);

            List<Edge> edges = v.edges;
            for (Edge edge : edges) {
                if (!visited.contains(edge.head)) {
                    Vertex edgeVertex = graph.vertices.get(edge.head);
                    heap.remove(edgeVertex);
                    edgeVertex.setMinCost(Math.min(edgeVertex.minCost, edge.cost));
                    heap.add(edgeVertex);
                }
            }

            mstMinCost += v.minCost;
            v = heap.poll();

        } while (v != null);

        return mstMinCost;
    }

    private static class Graph {
        private final Map<Integer, Vertex> vertices = new HashMap<>();

        private void addEdge(Edge e) {
            Vertex tail = getOrCreateVertex(e.tail);
            Vertex head = getOrCreateVertex(e.head);

            tail.addEdge(e);
            head.addEdge(new Edge(e.head, e.tail, e.cost));
        }

        private Vertex getOrCreateVertex(int id) {
            Vertex vertex = vertices.get(id);

            if (vertex == null) {
                vertex = new Vertex(id);
                vertices.put(id, vertex);
            }

            return vertex;
        }
    }

    private static class Vertex implements Comparable<Vertex> {
        private final int id;
        private final List<Edge> edges = new ArrayList<>();
        private int minCost = Integer.MAX_VALUE;

        private Vertex(int id) {
            this.id = id;
        }

        private void addEdge(Edge e) {
            edges.add(e);
        }

        private void setMinCost(int minCost) {
            this.minCost = minCost;
        }

        @Override
        public int compareTo(Vertex o) {
            return Integer.compare(minCost, o.minCost);
        }
    }

    private static class Edge {
        private final int head;
        private final int tail;
        private final int cost;

        public Edge(int tail, int head, int cost) {
            this.head = head;
            this.tail = tail;
            this.cost = cost;
        }
    }

    private static void debug(String message, Object... args) {
        System.out.println(String.format(message, args));
    }

    private static void info(String message, Object... args) {
        System.out.println(String.format(message, args));
    }

    private static Graph loadGraph(String path) throws Exception {
        List<int[]> rawGraph = Files.loadGraph(path);
        Graph graph = new Graph();
        rawGraph.stream().skip(1).forEach(edge -> graph.addEdge(new Edge(edge[0], edge[1], edge[2])));
        return graph;
    }
}
