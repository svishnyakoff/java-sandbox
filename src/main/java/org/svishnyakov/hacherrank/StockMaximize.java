package org.svishnyakov.hacherrank;

import java.util.Scanner;

public class StockMaximize {

    static long stockmax(int[] prices) {
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

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
//        Scanner in = new Scanner(StockMaximize.class.getResourceAsStream("/hackerrank/test2.txt"));
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
