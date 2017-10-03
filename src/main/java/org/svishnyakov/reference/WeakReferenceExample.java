package org.svishnyakov.reference;

import org.junit.Test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashSet;

public class WeakReferenceExample {

    private ReferenceQueue<Object> queue = new ReferenceQueue<>();

    private HashSet<WeakReference> treeSet = new HashSet();

    @Test
    public void testSoftReferenceDeallocation() {
        for (int i = 0; i < 100_000; i++) {
            Entry entry = new Entry();
            WeakReference softReference = new WeakReference<Object>(entry, queue);
            treeSet.add(softReference);
        }

        Object ref;
        while ((ref = queue.poll()) != null) {
            treeSet.remove(ref);
        }

        System.out.println("Left objects after the garbage collection: " + treeSet.size());
    }

    private static class Entry {
        int[] array = new int[20000];

        public Entry() {
            for (int i = 0; i < array.length; i++) {
                array[i] = i;
            }
        }

        public int[] getArray() {
            return this.array;
        }
    }
}
