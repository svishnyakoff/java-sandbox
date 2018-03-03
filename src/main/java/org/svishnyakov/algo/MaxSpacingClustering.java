package org.svishnyakov.algo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of max spacing clustering that is based on the Kruskal MST algo.
 * Course 3, week 2.
 */
public class MaxSpacingClustering {

    public static void main(String[] args) throws Exception {
        List<int[]> rawGraph = Files.loadGraph("/algo/clustering.txt");

        List<Edge> edges = rawGraph.stream()
                .skip(1)
                .map(array -> new Edge(array[0], array[1], array[2]))
                .sorted(Comparator.comparing(Edge::getCost))
                .collect(Collectors.toList());

        long actualNumberOfClusters = rawGraph.get(0)[0];
        long maxDistance = 0;

        Clusters clusters = new Clusters(IntStream.rangeClosed(1, 500).boxed().collect(Collectors.toSet()));

        for (Edge edge : edges) {
            Vertex tailCluster = clusters.findCluster(edge.tail);
            Vertex headCluster = clusters.findCluster(edge.head);

            if (tailCluster != headCluster) {
                if (actualNumberOfClusters > 4) {
                    clusters.union(edge.tail, edge.head);
                    actualNumberOfClusters--;
                }
                else {
                    maxDistance = edge.cost;
                    break;
                }
            }
        }

        printInfo("Maximum distance across clusters: %s", maxDistance);
    }

    /**
     * Find-union data structure that's having path compression optimization.
     */
    static class Clusters {
        private Map<Integer, Vertex> vertices = new HashMap<>();

        private Clusters(Set<Integer> vertices) {
            this.vertices = vertices.stream().collect(Collectors.toMap(Function.identity(), id -> new Vertex(id, 0 )));
        }

        Vertex findCluster(int vertexId) {
            Vertex v = vertices.get(vertexId);

            if (v == null) {
                return null;
            }

            Vertex parent = v.parent;
            while (parent != v) {
                v = parent;
                parent = v.parent;
            }

            compressPath(vertexId, v);

            return v;
        }

        void compressPath(int vertexId, Vertex rootVertex) {
            Vertex v = vertices.get(vertexId);
            Vertex parent = v.parent;

            while (parent != rootVertex) {
                v.parent = rootVertex;
                v = parent;
                parent = v.parent;
            }
        }

        void union(int v1, int v2) {
            printDebug("Union %s and %s", v1, v2);
            Vertex v1Cluster = findCluster(v1);
            Vertex v2Cluster = findCluster(v2);

            if (v1Cluster.rank > v2Cluster.rank) {
                v2Cluster.parent = v1Cluster;
            }
            else if (v2Cluster.rank > v1Cluster.rank) {
                v1Cluster.parent = v2Cluster;
            }
            else {
                Vertex newParent = new Vertex(v1Cluster.id, v1Cluster.rank + 1);
                v1Cluster.parent = newParent;
                v2Cluster.parent = newParent;
                printDebug("Clusters with equal rank. %s is new parent with rank %s", newParent.id, newParent.rank);
            }
        }
    }

    static class Vertex {
        private final int id;
        private final int rank;
        private Vertex parent;

        Vertex(int id, int rank) {
            this.id = id;
            this.rank = rank;
            this.parent = this;
        }
    }

    static class Edge {
        private final int head;
        private final int tail;
        private final int cost;

        Edge(int tail, int head, int cost) {
            this.head = head;
            this.tail = tail;
            this.cost = cost;
        }

        int getCost() {
            return this.cost;
        }
    }

    static void printInfo(String message, Object... args) {
        System.out.println(String.format(message, args));
    }

    static void printDebug(String message, Object... args) {
        System.out.println(String.format(message, args));
    }
}
