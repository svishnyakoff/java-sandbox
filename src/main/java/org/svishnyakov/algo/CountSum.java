package org.svishnyakov.algo;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 Your task is to compute the number of target values t
 in the interval [-10000,10000] (inclusive) such that there are distinct numbers x,y
 in the input file that satisfy x+y=t
 */
public class CountSum {

    public static void main(String[] args) throws Exception {

        long[] numberArray = loadArray("/algo/algo1-programming_prob-2sum.txt");
        Set<Long> resultSet = new HashSet<>();

        Arrays.sort(numberArray);

        int i = 0;
        int j = numberArray.length - 1;

        while (i < j && numberArray[i] != numberArray[j]) {
            long sum = numberArray[i] + numberArray[j];
            int k = i;

            while (sum >= - 10000 && sum <= 10000) {
                resultSet.add(sum);
                k++;

                sum = numberArray[k] + numberArray[j];
            }

            if (sum > 10000) {
                j--;
            }
            else {
                i++;
            }
        }

        System.out.println("Result: " + resultSet.size());
    }

    static long[] loadArray(String filePath) throws Exception {
        Stream<String> lines = java.nio.file.Files.lines(Paths.get(CountInversion.class.getResource(filePath).toURI()));
        return lines.mapToLong(Long::valueOf).toArray();
    }
}
