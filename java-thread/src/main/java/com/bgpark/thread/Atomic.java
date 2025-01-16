package com.bgpark.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Atomic {

    private static volatile boolean stop = false;
    private static volatile int count = 100;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                while (!stop) {}
                System.out.println(--count);
                System.out.println("thread=%s, count=%d".formatted(Thread.currentThread().getName(), count));
            });
        }
        Thread.sleep(1000L);
        stop = true;
        executor.shutdown();
        Thread.sleep(3000L);
        System.out.println(count == 0);
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
    }


}
