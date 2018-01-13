package org.svishnyakov.algo;

import com.google.common.base.Objects;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Shortest path following Dijkstra algorithm using heap data structure under the hood.
 * Week 2
 */
public class DijkstraShortestPath {

    public static void main(String[] args) throws Exception {

        Graph graph = loadGraph("/algo/dijkstraData.txt");

        Map<Integer, Integer> shortestPaths = findShortestPath(graph, 1);

        String result = Stream.of(7, 37, 59, 82, 99, 115, 133, 165, 188, 197)
                .map(shortestPaths::get)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        System.out.println("Result:" + result);
    }


    private static Map<Integer, Integer> findShortestPath(Graph graph, int startVertexKey) {

        Map<Integer, Integer> distances = new HashMap<>();

        Vertex startVertex = graph.removeWithKey(startVertexKey).get().withScore(0);
        distances.put(startVertexKey, 0);
        updateAdjustedVertices(graph, startVertex);

        while (!graph.isEmpty()) {
            Vertex currentVertex = graph.pollMinScoreVertex();
            log("Processed vertex:" + currentVertex.key + " with score " + currentVertex.score);
            distances.put(currentVertex.key, currentVertex.score);

            updateAdjustedVertices(graph, currentVertex);
        }

        return distances;
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private static void updateAdjustedVertices(Graph graph, Vertex vertex) {
        vertex.neighbors.forEach(neighbor -> {
            Optional<Vertex> neighborVertex = graph.removeWithKey(neighbor.adjustedVertex);

            if (neighborVertex.isPresent()) {
                graph.add(neighborVertex.get().withScore(Math.min(vertex.score + neighbor.length,
                                                                  neighborVertex.get().score)));
            }
        });
    }

    private static class Graph {
        private TreeSet<Vertex> vertices = new TreeSet<>();
        private Map<Integer, Vertex> vertexMapping = new HashMap<>();

        public void add(Vertex vertex) {
            vertexMapping.put(vertex.key, vertex);
            vertices.add(vertex);
        }

        Vertex pollMinScoreVertex() {
            Vertex vertex = vertices.pollFirst();
            vertexMapping.remove(vertex.key);
            return vertex;
        }

        boolean isEmpty() {
            return vertices.isEmpty();
        }

        Optional<Vertex> removeWithKey(Integer key) {
            Vertex vertex = vertexMapping.get(key);
            vertexMapping.remove(key);

            if (vertex == null) {
                return Optional.empty();
            }

            vertices.remove(vertex);
            return Optional.of(vertex);
        }
    }

    private static class Vertex implements Comparable<Vertex> {
        private static final Comparator<Vertex> COMPARATOR = Comparator.comparing(Vertex::getScore)
                .thenComparing(Vertex::getKey);
        private final List<Edge> neighbors;
        private final Integer key;
        private final Integer score;

        private Vertex(Integer key, Integer score, List<Edge> neighbors) {
            this.neighbors = neighbors;
            this.key = key;
            this.score = score;
        }

        Vertex withScore(int score) {
            return new Vertex(key, score, this.neighbors);
        }

        Integer getScore() {
            return this.score;
        }

        Integer getKey() {
            return this.key;
        }

        @Override
        public int compareTo(Vertex o) {
            return COMPARATOR.compare(this, o);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Vertex vertex = (Vertex) o;

            return Objects.equal(this.key, vertex.key)
                    && Objects.equal(this.score, vertex.score);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.key, this.score);
        }
    }

    private static class Edge {
        private final Integer adjustedVertex;
        private final Integer length;

        Edge(Integer adjustedVertex, Integer length) {
            this.adjustedVertex = adjustedVertex;
            this.length = length;
        }
    }

    static Graph loadGraph(String filePath) throws Exception {
        Stream<String> lines = java.nio.file.Files.lines(Paths.get(CountInversion.class.getResource(filePath).toURI()));
        List<Vertex> vertices = lines.map(line -> {
            int[] tokens = Stream.of(line.split(",|\t")).mapToInt(Integer::valueOf).toArray();
            int key = tokens[0];
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < tokens.length; i += 2) {
                edges.add(new Edge(tokens[i], tokens[i + 1]));
            }
            return new Vertex(key, 1000000, edges);
        }).collect(Collectors.toList());

        Graph graph = new Graph();
        vertices.forEach(graph::add);

        return graph;
    }
}
