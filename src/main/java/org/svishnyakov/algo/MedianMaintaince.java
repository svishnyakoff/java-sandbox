package org.svishnyakov.algo;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Find the median element is the costantly modified collection in a O(log n) time using two heaps
 */
public class MedianMaintaince {

    public static void main(String[] args) throws Exception {
        int[] array = Files.loadArray("/algo/median.txt");
        int sumOfMedians = countMedians(array);
        log("Sum of medians: " + sumOfMedians);
    }

    private static int countMedians(int[] array) {
        PriorityQueue<Integer> low = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> high = new PriorityQueue<>();

        int sumOfMedians = 0;

        for (int e : array) {
            PriorityQueue<Integer> targetHeap = low.size() <= high.size() ? low : high;

            targetHeap.add(e);
            rebalance(low, high);

            log("Median: " + low.peek());

            sumOfMedians = (sumOfMedians + low.peek()) % 10000;

        }

        return sumOfMedians;
    }

    private static void rebalance(PriorityQueue<Integer> low, PriorityQueue<Integer> high) {
        if (low.isEmpty() || high.isEmpty()) {
            return;
        }

        int lowElement = low.peek();
        int highElement = high.peek();

        if (lowElement > highElement) {
            low.poll();
            low.add(highElement);

            high.poll();
            high.add(lowElement);
        }
    }

    static void log(String message) {
        System.out.println(message);
    }
}
