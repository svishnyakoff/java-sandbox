package org.svishnyakov.hacherrank;

import java.util.Scanner;

public class BitManipulationSingleInt {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int a[] = new int[n];
        for (int a_i = 0; a_i < n; a_i++) {
            a[a_i] = in.nextInt();
        }

        int result = 0;
        for (int number : a) {
            result ^= number;
        }

        System.out.println(result);
    }
}
