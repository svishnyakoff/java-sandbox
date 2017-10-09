package org.svishnyakov.interview.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * https://github.com/mre/the-coding-interview/tree/master/problems/anagram-detection
 */
public class AnagramDetection {

    private static Integer[] primes = primeNums();

    private static int countAnagrams(String original, String substring) {
        int subHash = countHash(substring);
        int lookupHash = countHash(original.substring(0, substring.length()));
        int sn = substring.length();

        int counter = 0;
        char[] orig = original.toCharArray();

        if (subHash == lookupHash) {
            counter++;
        }

        for (int i = sn; i < original.length(); i++) {
            lookupHash -= getCode(orig[i - sn]);
            lookupHash += getCode(orig[i]);

            if(lookupHash == subHash) {
                counter++;
            }
        }

        return counter;
    }

    private static int getCode(char c) {
        return primes[c];
    }

    private static int countHash(String str) {
        int hash = 0;
        for (char c : str.toCharArray()) {
            hash += getCode(c);
        }

        return hash;
    }

    // todo fast and best way to get prime numbers
    private static Integer[] primeNums() {
        Boolean[] allNum = new Boolean[1000];
        Arrays.setAll(allNum, (int i) -> true);

        for(int i = 2; i < allNum.length; i++) {
            if (allNum[i]) {
                for (int j = i * 2; j < allNum.length; j = j + i) {
                    allNum[j] = false;
                }
            }
        }

        List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 1; i < allNum.length; i++) {
            if (allNum[i]) {
                primeNumbers.add(i);
            }
        }

        return primeNumbers.toArray(new Integer[0]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(countAnagrams(scanner.nextLine(), scanner.nextLine()));
    }
}
