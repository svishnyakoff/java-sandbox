package org.svishnyakov.hacherrank;

import java.util.Scanner;
import java.util.Stack;

public class QueueUsingTwoStacks {

    public static void main(String[] args) {
        MyQueue queue = new MyQueue();

        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        for (int i = 0; i < n; i++) {
            int operation = scan.nextInt();
            if (operation == 1) { // enqueue
                queue.enqueue(scan.nextInt());
            } else if (operation == 2) { // dequeue
                queue.dequeue();
            } else if (operation == 3) { // print/peek
                System.out.println(queue.peek());
            }
        }
        scan.close();
    }

    static class MyQueue {
        Stack<Integer> first = new Stack<>();
        Stack<Integer> second = new Stack<>();

        void enqueue(Integer i) {
            first.push(i);
        }

        Integer dequeue() {
            if (second.isEmpty()) {
                while (!first.isEmpty()) {
                    Integer number = first.pop();
                    second.push(number);
                }
            }

            return second.pop();
        }

        Integer peek() {
            if (second.isEmpty()) {
                while (!first.isEmpty()) {
                    Integer number = first.pop();
                    second.push(number);
                }
            }

            return second.peek();
        }
    }
}
