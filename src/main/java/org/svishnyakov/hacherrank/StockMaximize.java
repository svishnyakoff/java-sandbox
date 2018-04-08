package org.svishnyakov.hacherrank;

import java.util.Scanner;

public class StockMaximize {

    /**
     * Dynamic solution that didn't pass all test cases
     * Memory: O(N^2)
     * Time: O(N^2)
     */
    static long dynamincStockmax(int[] prices) {
        int size = prices.length + 1;
        long[] array = new long[size];

        for (int i = 1; i < size; i++) {
            array[i] = Long.MIN_VALUE;
        }

        long profit = 0;

        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                    long buy = array[j - 1] - prices[i];
                    array[j] = Math.max(array[j] , buy);

                    profit = Math.max(profit, array[j] + j * prices[i + 1]);
            }

             array[0] = profit;
        }

        return profit;
    }


    /**
     * Walk end -> start using more like greedy algorithm than dynamic
     * Time: O(n)
     * Memory O(1)
     */
    static long stockmax(int[] prices) {
        int maxPrice = 0;
        long profit = 0;

        for (int i = prices.length - 1; i >= 0; i--) {
            if (maxPrice >= prices[i]) {
                profit += maxPrice - prices[i];
            }
            else {
                maxPrice = prices[i];
            }
        }

        return profit;
    }



    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int n = in.nextInt();
            int[] prices = new int[n];
            for(int prices_i = 0; prices_i < n; prices_i++){
                prices[prices_i] = in.nextInt();
            }
            long result = stockmax(prices);
            System.out.println(result);
        }
        in.close();
    }
}
