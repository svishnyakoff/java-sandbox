package org.svishnyakov.hacherrank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Mix of greedy algorithm with of usage heap data structure.
 *
 * You are given orders (order time and how much time it takes to prepare the order)
 *
 * You need to minimize waiting time
 *
 * @see https://www.hackerrank.com/challenges/minimum-average-waiting-time/problem
 */
public class MinimumAverageWaitingTime {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PriorityQueue<Order> heap = new PriorityQueue<>();

        int n = scanner.nextInt();
        PriorityQueue<Order> orders = new PriorityQueue<>(Comparator.comparing(Order::getCookingTime));

        for (int i = 0; i < n; i++) {
            orders.add(new Order(scanner.nextInt(), scanner.nextInt()));
        }

        long currentTime = 0;

        List<Long> waitingTimes = new ArrayList<>();

        while (!orders.isEmpty()) {
            if (heap.isEmpty() || orders.peek().orderTime <= currentTime) {
                Order order = orders.poll();
                heap.add(order);
                if (currentTime < order.orderTime) {
                    currentTime = order.orderTime;
                }
            }
            else {
                Order order = heap.poll();
                currentTime += order.cookingTime;
                waitingTimes.add(currentTime - order.orderTime);
            }
        }

        while (!heap.isEmpty()) {
            Order order = heap.poll();
            currentTime += order.cookingTime;
            waitingTimes.add(currentTime - order.orderTime);
        }

        long average = (long) waitingTimes.stream().mapToLong(Long::longValue).average().getAsDouble();
        System.out.println("Waiting times: " + waitingTimes);
        System.out.println("Average: " + average);

    }

    static class Order implements Comparable<Order>{
        private final long orderTime;
        private final long cookingTime;

        public Order(long orderTime, long cookingTime) {
            this.orderTime = orderTime;
            this.cookingTime = cookingTime;
        }

        @Override
        public int compareTo(Order o) {
            return Long.compare(this.cookingTime, o.cookingTime);
        }

        public long getOrderTime() {
            return this.orderTime;
        }

        public long getCookingTime() {
            return this.cookingTime;
        }
    }
}
