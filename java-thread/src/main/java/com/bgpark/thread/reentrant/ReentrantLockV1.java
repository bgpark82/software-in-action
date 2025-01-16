package com.bgpark.thread.reentrant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockV1 {

    private final ReentrantLock lock = new ReentrantLock(true);
    private int count = 0;

    public void increment() {
        // acquire lock
        lock.lock();
        try {
            count++;
            System.out.println(Thread.currentThread().getName() + " incremented count to " + count);
        } finally {
            // always release the lock in the final block
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockV1 reentrantLockV1 = new ReentrantLockV1();
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(reentrantLockV1::increment);
        }

        Thread.sleep(3000L);

        executorService.shutdown();
    }
}
