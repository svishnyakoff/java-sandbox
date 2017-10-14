package org.svishnyakov.interview.task;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class BalancedBrackets {

    private static final HashMap<Character, Character> BRACKETS = new HashMap<Character, Character>() {
        {
            put('(', ')');
            put('[', ']');
            put('{', '}');

        }
    };

    private static final HashMap<Character, Character> REV_BRACKETS = new HashMap<Character, Character>() {
        {
            put(')', '(');
            put(']', '[');
            put('}', '{');

        }
    };

    public static void main(String[] args) {
        System.out.println(isBalanced("({[]})"));
    }

    private static boolean isBalanced(String str) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : str.toCharArray()) {
            if (BRACKETS.containsKey(c)) {
                stack.push(c);
            }
            else {
                Character prev = stack.pop();
                if (REV_BRACKETS.get(c) != prev) {
                    return false;
                }
            }
        }

        return true;
    }
}
