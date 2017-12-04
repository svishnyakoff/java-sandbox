package org.svishnyakov.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.svishnyakov.algo.Files.loadArray;

/**
 * Count number of inversion by leveraging merge sort algorithm.
 *
 * For example: given array [3, 2, 1]. There are 3 inversions here (3, 1), (3, 2) and (2, 1)
 * The inversion considered to be pair of numbers where {@literal i < j and array[i] > array[j]}
 */
public class CountInversion {

    private static long totalInversions = 0;

    public static void main(String[] args) throws Exception {
        int[] numbers = loadArray("/algo/countInversions.txt");

        int[] sortedNumbers = countAndSort(numbers);
        System.out.println("Sorted arrays: " + Arrays.toString(sortedNumbers));
        System.out.println("Total number of inversions: " + totalInversions);

    }

    private static int[] countAndSort(int[] numbers) {
        if (numbers.length < 2) {
            return numbers;
        }

        int splitNumber = numbers.length / 2;
        // The fact that we copy arrays here does not make out algorithm super fast, but I preferred this approach to
        // the solution where I pass the same array but have to play around with start and end indexes.
        int[] firstHalf = countAndSort(Arrays.copyOfRange(numbers, 0, splitNumber));
        int[] secondHalf = countAndSort(Arrays.copyOfRange(numbers, splitNumber, numbers.length));
        return mergeAndCountInversions(firstHalf, secondHalf);
    }

    private static int[] mergeAndCountInversions(int[] firstHalfSorted, int[] secondHalfSorted) {
        List<Integer> sortedResult = new ArrayList<>(firstHalfSorted.length + secondHalfSorted.length);
        int f = 0;
        int s = 0;
        while (f < firstHalfSorted.length && s < secondHalfSorted.length) {
            if (secondHalfSorted[s] <= firstHalfSorted[f]) {
                totalInversions += firstHalfSorted.length - f;
                sortedResult.add(secondHalfSorted[s]);
                s++;
            }
            else {
                sortedResult.add(firstHalfSorted[f]);
                f++;
            }
        }

        if (f < firstHalfSorted.length) {
            for (int i = f; i < firstHalfSorted.length; i++) {
                sortedResult.add(firstHalfSorted[i]);
            }
        }
        else if (s < secondHalfSorted.length) {
            for (int i = s; i < secondHalfSorted.length; i++) {
                sortedResult.add(secondHalfSorted[i]);
            }
        }

        return sortedResult.stream().mapToInt(i -> i).toArray();
    }
}
