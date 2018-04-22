package org.svishnyakov.hacherrank;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Dynamic programming The Maximum Subarray
 * https://www.hackerrank.com/challenges/maxsubarray/problem
 */
public class MaximumSubarray {

    static long[] maxSubarray(int[] arr) {
        int maxElement = Arrays.stream(arr).max().getAsInt();
        long solution[] = new long[arr.length];

        solution[0] = arr[0];

        for (int i = 1; i < arr.length; i++) {
            solution[i] = Math.max(solution[i - 1] + arr[i], arr[i]);
        }

        long maxSubArray = Arrays.stream(solution).max().getAsLong();
        long maxSubSeq = maxElement < 0 ? maxElement :
                Arrays.stream(arr).boxed().mapToLong(Integer::longValue).filter(a -> a >= 0).sum();

        long result[] = new long[2];
        result[0] = maxSubArray;
        result[1] = maxSubSeq;

        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int n = in.nextInt();
            int[] arr = new int[n];
            for(int arr_i = 0; arr_i < n; arr_i++){
                arr[arr_i] = in.nextInt();
            }
            long[] result = maxSubarray(arr);
            for (int i = 0; i < result.length; i++) {
                System.out.print(result[i] + (i != result.length - 1 ? " " : ""));
            }
            System.out.println("");


        }
        in.close();
    }
}
