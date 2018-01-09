package org.svishnyakov.algo;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Files {

    static int[] loadArray(String filePath) throws Exception {
        Stream<String> lines = java.nio.file.Files.lines(Paths.get(CountInversion.class.getResource(filePath).toURI()));
        return lines.mapToInt(Integer::valueOf).toArray();
    }

    static List<int[]> loadGraph(String filePath) throws Exception {
        Stream<String> lines = java.nio.file.Files.lines(Paths.get(CountInversion.class.getResource(filePath).toURI()));
        return lines.map(Files::parseIntArray).collect(Collectors.toList());
    }

    private static int[] parseIntArray(String str) {
        return Stream.of(str.split("\\s")).mapToInt(Integer::valueOf).toArray();
    }
}
