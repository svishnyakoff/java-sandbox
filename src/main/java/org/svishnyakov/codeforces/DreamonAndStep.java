package org.svishnyakov.codeforces;

import java.util.Scanner;

/**
 * Task http://codeforces.com/problemset/problem/476/A
 *
 * Resolution: Solved
 */
public class DreamonAndStep {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int totalSteps = scanner.nextInt();
        int m = scanner.nextInt();

        int bigSteps = totalSteps / 2;
        int madeSteps = bigSteps + totalSteps % 2;

        int leftSteps = madeSteps % m == 0 ? 0 : m - madeSteps % m;

        if (leftSteps > bigSteps) {
            System.out.println(-1);
            return;
        }

        madeSteps += leftSteps;

        System.out.println(madeSteps);
    }
}
