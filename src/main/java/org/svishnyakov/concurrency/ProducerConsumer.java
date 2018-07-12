package org.svishnyakov.concurrency;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class ProducerConsumer {

    private static class BlockingQueueProducerConsumer {

        private final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1);

        public Integer produce() {
            try {
                return queue.take();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        public void consume(Integer number) {
            try {
                queue.put(number);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class SemaphoreProducerConsumer {

        private final Semaphore availableReads = new Semaphore(0);
        private final Semaphore availableWrites = new Semaphore(1);
        private ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

        public Integer produce() throws InterruptedException {
            availableReads.acquire();
            Integer value = queue.poll();
            availableWrites.release();
            return value;
        }

        public void consume(Integer number) throws InterruptedException {
            availableWrites.acquire();
            queue.add(number);
            availableReads.release();
        }
    }

    @Test
    public void testBlockingQueue() throws InterruptedException {
        BlockingQueueProducerConsumer producerConsumer = new BlockingQueueProducerConsumer();

        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch completionLatch = new CountDownLatch(100);
        Thread producer = new Thread(() -> {
            try {
                latch.await();
                for (int i = 0; i < 100; i++) {
                    producerConsumer.consume(i);
                    System.out.println("--> " + i);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                latch.await();
                for (int i = 0; i < 100; i++) {
                    System.out.println("<-- " + producerConsumer.produce());
                    completionLatch.countDown();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();

        latch.countDown();

        completionLatch.await();
    }


    @Test
    public void testSemaphoreSolution() throws InterruptedException {
        SemaphoreProducerConsumer producerConsumer = new SemaphoreProducerConsumer();

        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch completionLatch = new CountDownLatch(100);
        Thread producer = new Thread(() -> {
            try {
                latch.await();
                for (int i = 0; i < 100; i++) {
                    producerConsumer.consume(i);
                    System.out.println("--> " + i);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                latch.await();
                for (int i = 0; i < 100; i++) {
                    System.out.println("<-- " + producerConsumer.produce());
                    completionLatch.countDown();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();

        latch.countDown();

        completionLatch.await();
    }
}
