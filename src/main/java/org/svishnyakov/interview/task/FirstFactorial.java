package org.svishnyakov.interview.task;

import java.util.Scanner;

public class FirstFactorial {

    public static int FirstFactorial(int num) {
        int facorial = 1;
        for (int i = 2; i <= num; i++) {
            facorial *= i;
        }

        return facorial;
    }

    public static void main (String[] args) {
        // keep this function call here
        Scanner s = new Scanner(System.in);
        System.out.print(FirstFactorial(Integer.parseInt(s.nextLine())));
    }
}
