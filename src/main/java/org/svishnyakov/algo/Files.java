package org.svishnyakov.algo;

import java.nio.file.Paths;
import java.util.stream.Stream;

class Files {

    static int[] loadArray(String filePath) throws Exception {
        Stream<String> lines = java.nio.file.Files.lines(Paths.get(CountInversion.class.getResource(filePath).toURI()));
        return lines.mapToInt(Integer::valueOf).toArray();
    }
}
