package org.svishnyakov.codeforces;

import java.util.Scanner;

/**
 * Task http://codeforces.com/problemset/problem/4/A
 *
 * Resolution: Solved
 */
public class Arbuz {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        int w = reader.nextInt();
        if(w > 2 && (w - 2) % 2 == 0) {
            System.out.println("YES");
        }
        else {
            System.out.printf("NO");
        }
    }
}
