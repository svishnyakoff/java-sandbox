package org.svishnyakov.algo;

import java.util.Arrays;

public class QuickSort {

    private static int[] array;
    private static int counter = 0;

    public static void main(String[] args) throws Exception {
        array = Files.loadArray("/algo/quickSort.txt");
        sort(0, array.length);
        System.out.println(Arrays.toString(array));
        System.out.println("Counter: " + counter);
    }

    private static void sort(int l, int r) {
        if (r - l < 2) {
            return;
        }

        counter += r - l - 1;

        int pivotIndex = checkPivot(l, r);
        swap(l, pivotIndex);
        int pivot = array[l];

        int i = l + 1;
        for (int j = l + 1; j < r; j++) {
            if (array[j] < pivot) {
                swap(j, i);
                i++;
            }
        }

        swap(i - 1, l);
        sort(l, i - 1);
        sort(i, r);
    }

    private static int checkPivot(int l, int r) {
        // Choose first element as a pivot
        return l;

        // Choose last element as a pivot
//        return r - 1;


        // Chose mean of three as a pivot
//        int a = l;
//        int b = r - 1;
//        int c = ((r - l - 1) / 2) + l;
//
//        Integer[] p = new Integer[]{a,b,c};
//        Arrays.sort(p, Comparator.comparingInt(s -> array[s]));
//        return p[1];
    }

    private static void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
