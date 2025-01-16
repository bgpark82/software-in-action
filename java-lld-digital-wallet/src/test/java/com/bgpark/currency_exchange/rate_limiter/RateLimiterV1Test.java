package com.bgpark.currency_exchange.rate_limiter;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class RateLimiterV1Test {

    @Test
    void allow_5_request_in_1_second() {
        RateLimiterV1 rateLimiter = new RateLimiterV1(5, 100);

        assertTrue(rateLimiter.tryAcquire());
        assertTrue(rateLimiter.tryAcquire());
        assertTrue(rateLimiter.tryAcquire());
        assertTrue(rateLimiter.tryAcquire());
        assertTrue(rateLimiter.tryAcquire());
        assertFalse(rateLimiter.tryAcquire());
    }

    @Test
    void allow_5_request_after_1_second() throws InterruptedException {
        RateLimiterV1 rateLimiter = new RateLimiterV1(5, 100);

        assertTrue(rateLimiter.tryAcquire());
        assertTrue(rateLimiter.tryAcquire());
        assertTrue(rateLimiter.tryAcquire());
        assertTrue(rateLimiter.tryAcquire());
        assertTrue(rateLimiter.tryAcquire());
        assertFalse(rateLimiter.tryAcquire());

        Thread.sleep(150);

        assertTrue(rateLimiter.tryAcquire());
    }

    @Test
    void allow_5_request_in_1_second_in_multi_threads() throws InterruptedException {
        RateLimiterV1 rateLimiter = new RateLimiterV1(5, 100);
        AtomicInteger count = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                if(rateLimiter.tryAcquire()) {
                    count.getAndIncrement();
                };
            });
        }

        executorService.shutdown();
        while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("Waiting for termination");
        }
        System.out.println(count.get());
    }
}