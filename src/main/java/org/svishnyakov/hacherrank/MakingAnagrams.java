package org.svishnyakov.hacherrank;

import java.util.Scanner;

public class MakingAnagrams {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String a = in.next();
        String b = in.next();
        System.out.println(numberNeeded(a, b));
    }


    public static int numberNeeded(String a, String b) {
        int[] numbers = new int[26];

        for (char c : a.toCharArray()) {
            numbers[c - 'a']--;
        }

        for (char c : b.toCharArray()) {
            numbers[c - 'a']++;
        }

        int result = 0;
        for (int i : numbers) {
            result += Math.abs(i);
        }

        return result;
    }
}
