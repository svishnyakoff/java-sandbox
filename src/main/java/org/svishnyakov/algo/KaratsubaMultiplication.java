package org.svishnyakov.algo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KaratsubaMultiplication {

    /**
     * Implementation of Karatsuba algorithm for more efficient multiplication comparing to grade school algorithm
     */
    private static Integer[] karatsubaMultiply(Integer[] first, Integer[] second) {

        if (first.length <= 2 || second.length <= 2) {
            return simpleMultiply(first, second);
        }

        int firstMiddleElement = (first.length + 1) / 2;
        Integer[] a  = Arrays.copyOfRange(first, 0, firstMiddleElement);
        Integer[] b  = Arrays.copyOfRange(first, firstMiddleElement, first.length);

        int secondMiddleElement = (second.length + 1) / 2;
        Integer[] c  = Arrays.copyOfRange(second, 0, secondMiddleElement);
        Integer[] d  = Arrays.copyOfRange(second, secondMiddleElement, second.length);

        Integer[] ac = karatsubaMultiply(a, c);
        Integer[] bd = karatsubaMultiply(b, d);

        Integer[] aPlusB = sum(a, b);
        Integer[] cPlusD = sum(c, d);
        Integer[] abcdProduct = simpleMultiply(aPlusB, cPlusD);
        Integer[] subract = subtract(abcdProduct, ac);
        subract = subtract(subract, bd);

        int halfPower = (Math.max(first.length, second.length) + 1) / 2;
        Integer[] result = sum(new Integer[0], bd, halfPower * 2);
        result = sum(result, ac, 0);
        return sum(result, subract, halfPower );
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
            int remain = 0;
            List<Integer> tempResult = new ArrayList<>();

            for (Integer s : second) {
                int prod = f * s + remain;
                int valueToAdd = prod % 10;
                remain = prod / 10;

                tempResult.add(valueToAdd);
            }

            if (remain != 0) {
                tempResult.add(remain);
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
