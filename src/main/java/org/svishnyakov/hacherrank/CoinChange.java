package org.svishnyakov.hacherrank;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class CoinChange {

    static long getWays(long n, long[] coins) {
        Arrays.sort(coins);

        return count(n, 0, 0, coins);
    }

    private static int count(long expectedValue, long currentValue, int index, long[] coins) {
        int counter = 0;

        if (currentValue == expectedValue) {
            return 1;
        }

        if (index > coins.length || currentValue + coins[index] > expectedValue) {
            return 0;
        }

        for (int i = index; i < coins.length; i++) {

            if (currentValue + coins[i] > expectedValue) {
                break;
            }

            counter += count(expectedValue, currentValue + coins[i], i, coins);

        }

        return counter;
    }

    static long getWaysDynamic(int n, int[] coins) {
        int maxCoin = 50;

        Set<Integer> coinSet = Arrays.stream(coins).boxed().collect(Collectors.toSet());

        long[][] array = new long[n + 1][maxCoin + 2];

        for (int j = 1; j <= maxCoin; j++) {
            if (coinSet.contains(j) && j <= n) {
                array[j][j] = 1;
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= maxCoin; j++) {
                if (coinSet.contains(j) && i + j <= n) {
                    array[i + j][j] = array[i + j][j] + array[i][j];
                }

                array[i][j + 1] = array[i][j + 1] + array[i][j];
            }
        }

        return array[n][50];
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[] c = new int[m];
        for (int c_i = 0; c_i < m; c_i++) {
            c[c_i] = in.nextInt();
        }
        // Print the number of ways of making change for 'n' units using coins having the values given by 'c'
        long ways = getWaysDynamic(n, c);

        System.out.println(ways);
    }
}
