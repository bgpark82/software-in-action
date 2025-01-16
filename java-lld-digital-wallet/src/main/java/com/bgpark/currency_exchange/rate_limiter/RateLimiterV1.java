package com.bgpark.currency_exchange.rate_limiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiterV1 {

    private final int maxRequest;
    private final long windowMillis;
    private final ConcurrentLinkedQueue<Long> requestTimestamp;
    private final AtomicInteger currentRequestCount;

    public RateLimiterV1(int maxRequest, long windowMillis) {
        this.maxRequest = maxRequest;
        this.windowMillis = windowMillis;
        this.requestTimestamp = new ConcurrentLinkedQueue<>();
        this.currentRequestCount = new AtomicInteger(0);
    }

    /**
     * Multiple threads can call this method
     * without synchronized keyword, it can be updated by multiple threads at the same time
     */
    public synchronized boolean tryAcquire() {
        // increase count
        // add timestamp
        long now = System.currentTimeMillis();
        cleanUp(now);

        System.out.println("queue=%d, count=%d".formatted(requestTimestamp.size(), currentRequestCount.get()));
        if (currentRequestCount.get() < maxRequest) {
            requestTimestamp.add(now);
            currentRequestCount.incrementAndGet();
            return true;
        }
        return false;
    }

    private void cleanUp(long now) {
        // remove queue
        // decrease count

        // it will empty queue
        while (!requestTimestamp.isEmpty() && now- requestTimestamp.peek() > windowMillis) {
            System.out.println("[cleanup] queue=%d, count=%d".formatted(requestTimestamp.size(), currentRequestCount.get()));
            requestTimestamp.poll();
            currentRequestCount.decrementAndGet();
        }
    }
}
