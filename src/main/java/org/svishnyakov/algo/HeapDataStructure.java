package org.svishnyakov.algo;

import java.util.ArrayList;

/**
 * Implementation of the heap using the array
 */
public class HeapDataStructure {

    public static void main(String[] args) {
        Heap heap = new Heap();

        heap.add(5);
        heap.add(4);
        heap.add(3);
        heap.add(2);
        heap.add(1);
        heap.add(6);
        heap.add(7);

        System.out.println(heap.array);
        for (int i = 0; i < 7; i++) {
            System.out.print(heap.pollMin());
        }
    }

    static class Heap {
        private ArrayList<Integer> array = new ArrayList<>();

        Integer pollMin() {
            if (array.isEmpty()) {
                throw new IndexOutOfBoundsException();
            }

            if (lastIndex() == 0) {
                return array.remove(lastIndex());
            }
            swap(0, lastIndex());
            int minElement = array.remove(lastIndex());

            bubbleDown(0);

            return minElement;
        }

        void add(Integer element) {
            int index = array.size();
            array.add(element);

            bubbleUp(index);
        }

        private void bubbleUp(int index) {
            while (index > 0) {
                int parentIndex = (index - 1) >>> 1;
                int parent = array.get(parentIndex);
                int current = array.get(index);

                if (parent <= current) {
                    return;
                }

                swap(index, parentIndex);
                index = parentIndex;
            }
        }

        private void bubbleDown(int index) {
            int half = array.size() >>> 1;

            while (index < half) { // while is not leaf
                int leftIndex = (index << 1) + 1;
                int rightIndex = leftIndex + 1;
                int left = array.get(leftIndex);

                int minIndex = rightIndex < array.size() && array.get(rightIndex) < left ? rightIndex : leftIndex;

                if (array.get(minIndex) < array.get(index)) {
                    swap(index, minIndex);
                    index = minIndex;
                    continue;
                }

                break;
            }
        }

        void swap(int first, int second) {
            int temp = array.get(first);
            array.set(first, array.get(second));
            array.set(second, temp);
        }

        int lastIndex() {
            return array.size() - 1;
        }
    }
}
