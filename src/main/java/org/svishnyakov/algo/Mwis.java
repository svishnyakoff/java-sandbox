package org.svishnyakov.algo;

import java.util.Arrays;

/**
 * Week 3 - dynamic programming.
 */
public class Mwis {

    public static void main(String[] args) throws Exception {
        int[] nodes = Files.loadArray("/algo/mwis.txt");

        Node[] array = Arrays.stream(nodes).skip(1)
                .boxed()
                .map(Node::new)
                .toArray(Node[]::new);

        array[0].value = array[0].weight;
        array[1].value = Math.max(array[0].weight, array[1].weight);

        for (int i = 2; i < array.length; i++) {
            Node node = array[i];
            node.value = Math.max(array[i - 1].value, array[i - 2].value + node.weight);
        }

        for (int i = array.length - 1; i >= 0; i--) {
            Node node = array[i];

            long a = i > 0 ? array[i - 1].value : 0;
            long b = i > 1 ? array[i - 2].value : 0;

            if (a <= (b + node.weight)) {
                node.included = true;
                i--;
            }
        }

        System.out.println("[1] " + array[0].included);
        System.out.println("[2] " + array[1].included);
        System.out.println("[3] " + array[2].included);
        System.out.println("[4] " + array[3].included);
        System.out.println("[17] " + array[16].included);
        System.out.println("[117] " + array[116].included);
        System.out.println("[517] " + array[516].included);
        System.out.println("[997] " + array[996].included);
    }

    static class Node {
        long value;
        boolean included = false;
        final long weight;

        Node(long weight) {
            this.weight = weight;
        }
    }
}
