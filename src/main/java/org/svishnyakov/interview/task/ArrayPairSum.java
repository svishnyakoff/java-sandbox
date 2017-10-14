package org.svishnyakov.interview.task;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Given an integer array, output all pairs that sum up to a specific value k. Consider the fact that the same number can
 * add up to k with its duplicates in the array.
 *
 * https://github.com/mre/the-coding-interview/tree/master/problems/array-pair-sum
 */
public class ArrayPairSum {

    public static void main(String[] args) {

        outputWithSet(10, new int[]{3, 4, 5, 6, 7}); // [[6, 4], [7, 3]]
        outputWithSet(8, new int[]{3, 4, 5, 4, 4}); // [[3, 5], [4, 4], [4, 4], [4, 4]]
        outputWithSet(8, new int[]{4}); // []
        outputWithSet(0, new int[]{4,-4}); // [[-4,4]]
    }

    /**
     * O(N) complexity
     */
    private static void outputWithSet(int sum, int[] array) {
        HashSet<Integer> seen = new HashSet<>();
        for (int i : array) {
            if (seen.contains(sum - i)) {
                System.out.println(String.format("[%d, %d]", i, sum - i));
            }
            seen.add(i);
        }
    }

    /**
     * O(N log N) complexity
     */
    private static void outputPairsWithSorting(int sum, int[] array) {
        Arrays.sort(array);

        int begin = 0;
        int end = array.length - 1;

        while (end > begin) {
            int s = array[begin] + array[end];

            if (s > sum) {
                end--;
            }
            else if (s < sum) {
                begin++;
            }
            else {
                System.out.println(String.format("[%d, %d]", array[begin], array[end]));

                if(array[end - 1] == array[end]) {
                    end--;
                }
                else {
                    begin++;
                }
            }
        }
    }
}
