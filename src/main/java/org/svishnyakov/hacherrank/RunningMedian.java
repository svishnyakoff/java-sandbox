package org.svishnyakov.hacherrank;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Find a median element using two heaps.
 *
 * https://www.hackerrank.com/challenges/ctci-find-the-running-median/problem
 */
public class RunningMedian {

    private static PriorityQueue<Integer> low = new PriorityQueue<>(Comparator.reverseOrder());
    private static PriorityQueue<Integer> high = new PriorityQueue<>();

    private static void balance() {
        if (low.isEmpty() || high.isEmpty()) {
            return;
        }

        if (low.peek() > high.peek()) {
            int a = low.poll();
            int b = high.poll();

            low.add(b);
            high.add(a);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n];
        for (int a_i = 0; a_i < n; a_i++) {
            a[a_i] = in.nextInt();

            PriorityQueue<Integer> target = low.size() <= high.size() ? low : high;
            target.add(a[a_i]);
            balance();

            if (a_i % 2 == 0) {
                System.out.println(String.format("%d.0", low.peek()));
            }
            else {
                //System.out.println("Size " + )
                System.out.println(String.format("%.1f", (low.peek() + high.peek()) / 2.0));
            }
        }
    }

}
