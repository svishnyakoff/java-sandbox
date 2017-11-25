package org.svishnyakov.algo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KaratsubaMultiplication {

    /**
     * Implementation of Karatsuba algorithm for more efficient multiplication comparing to grade school algorithm
     *
     * Algorithm: x = 1234, y = 5678
     * a = 12; b = 34; c = 56; d = 78;
     *
     * 1. Find ac
     * 2. Find bd
     * 3. t = (a + b)*(c + d) - ac - bd
     *
     * Result = bd * 10^n + t * 10^(n/2) + ac
     */
    private static Integer[] karatsubaMultiply(Integer[] x, Integer[] y) {

        if (x.length <= 2 || y.length <= 2) {
            return simpleMultiply(x, y);
        }

        int xMedian = (x.length + 1) / 2;
        Integer[] xl  = Arrays.copyOfRange(x, 0, xMedian);
        Integer[] xu  = Arrays.copyOfRange(x, xMedian, x.length);

        int yMedian = (y.length + 1) / 2;
        Integer[] yl  = Arrays.copyOfRange(y, 0, yMedian);
        Integer[] yu  = Arrays.copyOfRange(y, yMedian, y.length);

        Integer[] xyLower = karatsubaMultiply(xl, yl);
        Integer[] xyUpper = karatsubaMultiply(xu, yu);

        Integer[] xSum = sum(xl, xu);
        Integer[] ySum = sum(yl, yu);
        Integer[] xySumProduct = simpleMultiply(xSum, ySum);
        Integer[] subract = subtract(xySumProduct, xyLower);
        subract = subtract(subract, xyUpper);

        int halfPower = (Math.max(x.length, y.length) + 1) / 2;
        Integer[] result = sum(new Integer[0], xyUpper, halfPower * 2);
        result = sum(result, xyLower);
        return sum(result, subract, halfPower);
    }

    /**
     * Grade school algorithm for multiplication of two numbers.
     */
    private static Integer[] simpleMultiply(Integer[] first, Integer[] second) {
        if (first.length == 0 || second.length == 0) {
            return new Integer[]{0};
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < first.length; i++) {
            int f = first[i];
            int rest = 0;
            List<Integer> tempResult = new ArrayList<>();

            for (Integer s : second) {
                int prod = f * s + rest;
                rest = prod / 10;
                tempResult.add(prod % 10);
            }

            if (rest != 0) {
                tempResult.add(rest);
            }

            sum(result, tempResult, i);
        }

        return result.toArray(new Integer[0]);
    }

    private static Integer[] subtract(Integer[] first, Integer[] second) {
        Integer[] result = Arrays.copyOf(first, first.length);

        int debt = 0;
        for (int i = 0; i < second.length; i++) {
            Integer firstNumber = first[i] - debt;
            Integer secondNumber = second[i];
            if (firstNumber - secondNumber < 0) {
                firstNumber += 10;
                debt = 1;
            } else {
                debt = 0;
            }

            result[i] = firstNumber - secondNumber;
        }

        int index = second.length;
        while (debt != 0 && index < first.length) {
            Integer firstNumber = first[index] - debt;
            if (firstNumber < 0) {
                firstNumber += 10;
                debt = 1;
            } else {
                debt = 0;
            }

            result[index] = firstNumber;
            index++;
        }

        int trailingZeros = 0;
        for (int i = result.length - 1; i >= 0; i--) {
            if (result[i] == 0) {
                trailingZeros++;
            } else {
                break;
            }
        }

        if (trailingZeros > 0) {
            result = Arrays.copyOfRange(result, 0, result.length - trailingZeros);
        }

        if (result.length == 0) {
            result = new Integer[]{0};
        }

        return result;
    }

    private static Integer[] sum(Integer[] first, Integer[] second, int padding) {
        List<Integer> result = new ArrayList<>();
        Collections.addAll(result, first);
        sum(result, Arrays.asList(second), padding);

        return result.toArray(new Integer[0]);
    }

    private static Integer[] sum(Integer[] first, Integer[] second) {
        List<Integer> result = new ArrayList<>();
        Collections.addAll(result, first);
        sum(result, Arrays.asList(second), 0);

        return result.toArray(new Integer[0]);
    }

    private static void sum(List<Integer> result, List<Integer> toMerge, int padding) {
        int remain = 0;
        for (int i = 0; i < toMerge.size(); i++) {
            int resultIndex = i + padding;
            while (result.size() < resultIndex + 1) {
                result.add(0);
            }
            int current = result.get(resultIndex);
            current += toMerge.get(i) + remain;
            remain = current / 10;
            result.set(resultIndex, current % 10);
        }

        int i = padding + toMerge.size();
        while (remain != 0) {
            if (result.size() < i + 1) {
                result.add(0);
            }
            int currentValue = result.get(i);
            int newValue = currentValue + remain;
            result.set(i, newValue % 10);
            remain = newValue / 10;
            i++;
        }
    }

    private static String toNumberString(Integer[] arrayNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer integer : arrayNumber) {
            stringBuilder.append(integer);
        }

        return stringBuilder.reverse().toString();
    }

    private static Integer[] toArray(String number) {
        Integer[] result = new Integer[number.length()];
        for (int i = number.length() - 1; i >= 0; i--) {
            char c = number.charAt(i);
            result[result.length - (i + 1)] = Integer.valueOf(String.valueOf(c));
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println("Grade school multiply");
        Integer[] result = simpleMultiply(toArray("3141592653589793238462643383279502884197169399375105820974944592"),
                                          toArray("2718281828459045235360287471352662497757247093699959574966967627"));
        System.out.println(toNumberString(result));

        System.out.println("Big Integer multiply");
        System.out.println(new BigDecimal("3141592653589793238462643383279502884197169399375105820974944592").multiply(
                new BigDecimal("2718281828459045235360287471352662497757247093699959574966967627")
        ));

        System.out.println("Karatsuba multiply");
        System.out.println(toNumberString(karatsubaMultiply(toArray("3141592653589793238462643383279502884197169399375105820974944592"),
                       toArray("2718281828459045235360287471352662497757247093699959574966967627"))));
    }
}
