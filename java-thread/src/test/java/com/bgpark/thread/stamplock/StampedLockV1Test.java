package com.bgpark.thread.stamplock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class StampedLockV1Test {

    @Test
    void stampedLock() {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        StampedLockV1 stampedLockV1 = new StampedLockV1();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> stampedLockV1.deposit(10));
        }
        executorService.shutdown();

        // it's not always 10000
        assertEquals(stampedLockV1.getBalance(), 10000);
    }
}