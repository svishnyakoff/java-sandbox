package org.svishnyakov.hacherrank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Find minim cost path using brute-force
 * https://www.hackerrank.com/challenges/beautiful-path/problem
 */
public class MinimumPenaltyPath {

    static int beautifulPath(int[][] edges, int A, int B) {
        Graph g = new Graph();
        for (int[] edge : edges) {
            g.updateVertex(edge);
        }

        if (g.get(A) == null || g.get(B) == null) {
            return -1;
        }

        g.get(A).visited = true;
        g.get(A).distances.add(0);
        lookUpNext(g, g.get(A), 0);

        Vertex bVertex = g.get(B);

        return bVertex.distances.stream().mapToInt(Integer::intValue).min().orElse(-1);
    }

    static void lookUpNext(Graph g, Vertex v, int currentDistance) {
        for (Edge edge : v.edges) {
            Vertex next = g.get(edge.vertexId);
            int newDistance = edge.cost | currentDistance;
            if (!next.visited && !next.distances.contains(newDistance)) {
                next.visited = true;
                next.addDistance(newDistance);
                lookUpNext(g, next, newDistance);
                next.visited = false;
            }
        }
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] edges = new int[m][3];
        for(int edges_i = 0; edges_i < m; edges_i++){
            for(int edges_j = 0; edges_j < 3; edges_j++){
                edges[edges_i][edges_j] = in.nextInt();
            }
        }
        int A = in.nextInt();
        int B = in.nextInt();
        int result = beautifulPath(edges, A, B);
        System.out.println(result);
        in.close();
    }

    static class Graph {
        Map<Integer, Vertex> vertices = new HashMap<>();


        Vertex addVertex(int vertexId) {
            return vertices.computeIfAbsent(vertexId, Vertex::new);
        }

        Vertex get(int id) {
            return vertices.get(id);
        }

        void updateVertex(int[] edge) {
            int tail = edge[0];
            int head = edge[1];
            int cost = edge[2];

            Vertex v = addVertex(tail);
            Vertex headVertex = addVertex(head);
            headVertex.add(new Edge(tail, cost));
            v.add(new Edge(head, cost));
        }

    }

    static class Vertex {

        final int vertexId;
        final List<Edge> edges = new ArrayList<>();
        final Set<Integer> distances = new HashSet<>();
        boolean visited = false;

        Vertex(int vertexId) {
            this.vertexId = vertexId;
        }

        void add(Edge e) {
            edges.add(e);
        }

        void addDistance(int distance) {
            distances.add(distance);
        }
    }

    static class Edge {
        int vertexId;
        int cost;

        Edge(int vertexId, int cost) {
            this.vertexId = vertexId;
            this.cost = cost;
        }
    }
}
