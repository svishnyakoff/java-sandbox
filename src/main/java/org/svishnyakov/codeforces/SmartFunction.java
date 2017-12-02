package org.svishnyakov.codeforces;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.Arrays.copyOfRange;

/**
 * Closest pair problem
 * http://codeforces.com/problemset/problem/429/D
 */
public class SmartFunction {

    static Element[] numbers;
    static Element[] sortedNumbers;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        numbers = new Element[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = new Element(i, scanner.nextInt());
        }

        for (int i = 1; i < n; i++) {
            numbers[i] = new Element(i, numbers[i].value + numbers[i - 1].value);
        }

        sortedNumbers = Arrays.copyOf(numbers, numbers.length);
        Arrays.sort(sortedNumbers, Comparator.comparingInt(Element::getValue));

        System.out.println(count(numbers, sortedNumbers));
    }

    private static long count(Element[] x, Element[] y) {
        if (x.length <= 4) {
            long min = Long.MAX_VALUE;
            for (int i = 0; i < x.length - 1; i++) {
                for (int j = i + 1; j < x.length; j++) {
                    min = Math.min(f(x[i], x[j]), min);
                }
            }
            return min;
        }

        Element mean = x[x.length / 2];
        Element[] qy = Stream.of(y).filter(element -> element.index < mean.index).toArray(Element[]::new);
        Element[] py = Stream.of(y).filter(element -> element.index >= mean.index).toArray(Element[]::new);

        long leftMin = count(copyOfRange(x, 0, x.length / 2), qy);
        long rightMin = count(copyOfRange(x, x.length / 2, x.length), py);
        long delta = Math.min(leftMin, rightMin);
        return Math.min(countSplitMin(x, y, delta), delta);
    }

    private static long countSplitMin(Element[] x, Element[] y, long delta) {
        Element median = x[x.length / 2 - 1];
        Element[] sY = Stream.of(y).filter(element -> Math.abs(median.index - element.index) <= Math.sqrt(delta))
                .toArray(Element[]::new);

        long min = delta;

        for (int i = 0; i < sY.length; i++) {
            for (int j = i + 1; j < Math.min(i + 7, sY.length); j++) {
                Element first = sY[i];
                Element second = sY[j];

                min = Math.min(f(first, second), min);
            }
        }

        return min;
    }

    private static long f(Element i, Element j) {
        return (long) (Math.pow(i.index - j.index, 2.0)  + Math.pow(i.value - j.value, 2));
    }
    
    private static class Element {
        private final int index;
        private final int value;

        public Element(int index, int value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return this.index;
        }

        public int getValue() {
            return this.value;
        }
    }
}
