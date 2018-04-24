package org.svishnyakov.hacherrank;

import java.util.Scanner;

/**
 * On each step we consider either:
 * 1. should take max of B[i], and compare it with min and max of B[i -1]
 * 2. should take min of B[i] (just 1), and compare it with min and max of B[i -1]
 *
 * https://www.hackerrank.com/challenges/sherlock-and-cost/problem
 */
public class SherlockAndCost {

    // Complete the cost function below.
    static long cost(int[] costs) {
        long[][] solution = new long[costs.length][2];

        for (int i = 1; i < costs.length; i++) {
            solution[i][1] = Math.max(solution[i - 1][1] + Math.abs(costs[i] - costs[i - 1]),
                                      solution[i - 1][0] + Math.abs(costs[i] - 1));

            solution[i][0] = Math.max(solution[i - 1][0] ,
                                      solution[i - 1][1] + Math.abs(costs[i - 1] - 1));
        }

        return Math.max(solution[costs.length - 1][0], solution[costs.length - 1][1]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int t = scanner.nextInt();

        for (int i = 0; i < t; i++) {
            int n = scanner.nextInt();
            int[] arr = new int[n];

            for (int j = 0; j < n; j++) {
                arr[j] = scanner.nextInt();
            }

            System.out.println(cost(arr));
        }
    }
}
