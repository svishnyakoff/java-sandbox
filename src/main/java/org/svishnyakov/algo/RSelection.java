package org.svishnyakov.algo;

import java.util.Arrays;
import java.util.Random;

public class RSelection {

    public static void main(String[] args) throws Exception {
        int[] numbers = Files.loadArray("/algo/quickSort.txt");
        int k = 97;
        System.out.println(select(numbers, k));
    }

    private static int select(int[] numbers, int k) {
        if (numbers.length == 1) {
            return numbers[0];
        }
        int pivotIndex = choosePivot(numbers);
        int pivot = numbers[pivotIndex];
        swap(numbers, 0, pivotIndex);

        // partition against the pivot
        int i = 1;
        for (int j = 1; j < numbers.length; j++) {
            int number = numbers[j];
            if (number < pivot) {
                swap(numbers, i, j);
                i++;
            }
        }

        swap(numbers, i - 1, 0);

        int newPivotIndex = i - 1;

        // Exact k-th element we were looking for
        if (newPivotIndex == k - 1) {
            return pivot;
        }

        // the k-th element should be on the left group of elements
        if (k - 1 < newPivotIndex) {
            return select(Arrays.copyOfRange(numbers, 0, newPivotIndex), k);
        }
        // the k-th element should be on the right group of elements
        else {
            return select(Arrays.copyOfRange(numbers, newPivotIndex + 1, numbers.length), k - 1 - newPivotIndex);
        }
    }

    private static int choosePivot(int[] numbers) {
        return new Random().nextInt(numbers.length);
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
