package org.svishnyakov.algo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Clustering based Hamming distance metric. For this problem given a huge data set that make it's impossible to calculate
 * edges for all vertices.
 *
 * Course 3, week 2
 */
public class HammingMaxSpacingClustering {

    public static void main(String[] args) throws Exception {
        List<int[]> rawGraph = Files.loadGraph("/algo/clustering_big.txt");

        Set<Integer> availableCodes = rawGraph.stream()
                .skip(1)
                .map(HammingMaxSpacingClustering::toNumber)
                .collect(Collectors.toSet());

        Clusters clusters = new Clusters(availableCodes);

        for (Integer currentCode : availableCodes) {

            List<Integer> closestVertices = Stream.concat(withHammingDistanceOne(toArray(currentCode)).stream(),
                                                          withHammingDistanceTwo(toArray(currentCode)).stream())
                    .filter(availableCodes::contains)
                    .collect(Collectors.toList());

            for (Integer closestVertexCode : closestVertices) {
                Vertex v1Cluster = clusters.findCluster(currentCode);
                Vertex v2Cluster = clusters.findCluster(closestVertexCode);
                if (v1Cluster != v2Cluster) {
                    clusters.union(currentCode, closestVertexCode);
                }
            }
        }

        printInfo("Number of K cluster: %s", clusters.countClusters());
    }

    static int toNumber(int[] array) {
        int number = 0;
        int mask = 1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                number ^= mask;
            }

            mask = mask << 1;
        }

        return number;
    }

    static int[] toArray(int i) {
        int[] array = new int[24];

        int mask = 1;

        for (int j = 0; j < array.length; j++) {
            array[j] = mask & i;
            i = i >> 1;
        }

        return array;
    }

    static Set<Integer> withHammingDistanceOne(int[] array) {
        Set<Integer> codes = new HashSet<>(24);
        for (int i = 0; i < array.length; i++) {
            int[] copy = Arrays.copyOf(array, array.length);
            copy[i] = copy[i] ^ 1;
            codes.add(toNumber(copy));
        }

        return codes;
    }

    static Set<Integer> withHammingDistanceTwo(int[] array) {
        Set<Integer> codes = new HashSet<>(600);
        for (int i = 0; i < array.length - 1; i++) {
            int[] copy = Arrays.copyOf(array, array.length);
            copy[i] = copy[i] ^ 1;
            for (int j = i + 1; j < array.length; j++) {
                copy[j] = copy[j] ^ 1;
                codes.add(toNumber(copy));
                copy[j] = copy[j] ^ 1;
            }
        }

        return codes;
    }

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

        long countClusters() {
            return vertices.values().stream()
                    .map(vertex -> findCluster(vertex.id))
                    .distinct()
                    .count();
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

    static void printInfo(String message, Object... args) {
        System.out.println(String.format(message, args));
    }

    static void printDebug(String message, Object... args) {
        System.out.println(String.format(message, args));
    }
}
