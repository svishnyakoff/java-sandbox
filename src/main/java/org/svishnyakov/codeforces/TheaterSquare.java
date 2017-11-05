package org.svishnyakov.codeforces;

import java.util.Scanner;

/**
 * Task http://codeforces.com/problemset/problem/1/A
 *
 * Resolution: Solved
 */
public class TheaterSquare {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        long n = reader.nextLong();
        long m = reader.nextLong();
        long a = reader.nextLong();

        long rowNum = (long)Math.ceil((double)m / a);
        long columnNum = (long) Math.ceil((double)n / a);

        System.out.println(rowNum * columnNum);
    }
}
