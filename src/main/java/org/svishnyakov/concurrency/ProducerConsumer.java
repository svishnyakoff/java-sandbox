package org.svishnyakov.concurrency;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class ProducerConsumer {

    private interface IProduceConsumer {
        Integer produce() throws InterruptedException;
        void consume(Integer number) throws InterruptedException;
    }

    private static class BlockingQueueProducerConsumer implements IProduceConsumer {

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

    private static class SemaphoreProducerConsumer implements IProduceConsumer{

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

    private static class IntrinsicMonitorProducerConsumer implements IProduceConsumer{

        private Integer value;

        public synchronized Integer produce() throws InterruptedException {
            while (value == null) {
                wait();
            }

            notifyAll();

            int result = value;
            value = null;

            return result;
        }

        public synchronized void consume(Integer number) throws InterruptedException {
            while (value != null) {
                wait();
            }

            notifyAll();

            value = number;
        }
    }

    @Test
    public void testProducerConsumers() throws InterruptedException {
        testProducerConsumer(new BlockingQueueProducerConsumer());
        testProducerConsumer(new SemaphoreProducerConsumer());
        testProducerConsumer(new IntrinsicMonitorProducerConsumer());
    }

    private void testProducerConsumer(IProduceConsumer producerConsumer) throws InterruptedException {

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
