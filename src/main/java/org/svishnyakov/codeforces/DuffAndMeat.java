package org.svishnyakov.codeforces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Task http://codeforces.com/problemset/problem/588/A
 *
 * Resolution: Solved
 */
public class DuffAndMeat {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        ArrayList<Map.Entry<Integer, Integer>> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int a = scanner.nextInt();
            int p = scanner.nextInt();
            list.add(new HashMap.SimpleEntry<>(a, p));
        }

        long totalCost = 0;
        int optimalPrice = list.get(0).getValue();

        for (Map.Entry<Integer, Integer> dayEntry : list) {
            int needed = dayEntry.getKey();
            int currentPrice = dayEntry.getValue();

            if (optimalPrice > currentPrice) {
                optimalPrice = currentPrice;
            }

            int cost = needed * optimalPrice;
            totalCost += cost;
        }

        System.out.println(totalCost);
    }
}
