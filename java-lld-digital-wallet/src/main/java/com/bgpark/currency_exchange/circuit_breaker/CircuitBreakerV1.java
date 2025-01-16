package com.bgpark.currency_exchange.circuit_breaker;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public class CircuitBreakerV1 {

    private enum State { CLOSED, OPEN, HALF_OPEN }

    private final int failureThreshold;
    private final int successThreshold;
    private final Duration waitDuration;

    private State state = State.CLOSED;
    private int failureCount = 0;
    private int successCount = 0;
    private Instant lastFailureTime;

    public CircuitBreakerV1(int failureThreshold, int successThreshold, Duration waitDuration) {
        this.failureThreshold = failureThreshold;
        this.successThreshold = successThreshold;
        this.waitDuration = waitDuration;
    }

    /**
     * if state is OPEN, we need to check if it is time to close it. if not, we need to return fallback
     * if state is CLOSED, we need to return action result
     * if state is HALF_OPEN, we need to check if it is time to close it. if not, we need to return fallback
     */
    public <T> T execute(SupplierWithException<T> action, Supplier<T> fallback) {
        // if circuit breaker is open, we need to check if it is time to close it
        if (state == State.OPEN) {
            // if duration between last failure time and now is greater than wait time,
            // it means that something is not working well. we need to open circuit breaker
            if (Duration.between(lastFailureTime, Instant.now()).compareTo(waitDuration) > 0) {
                state = State.HALF_OPEN;
            // otherwise, we need to return fallback
            // because circuit breaker is already open
            } else {
                return fallback.get();
            }
        }

        // otherwise, we need to execute the action
        // state is either CLOSED or HALF_OPEN
        try {
            T result = action.get();

            // Check if its half open state
            if (state == State.HALF_OPEN) {
                successCount++;
                // if success count is greater than success threshold
                // now we can close, we don't need to check anymore
                if (successCount >= successThreshold) {
                    closeCircuit();
                }
            // CLOSED state
            } else {
                closeCircuit();
            }
            return result;
        } catch (Exception e) {
            // increase failure count
            // set last failure time

            failureCount++;
            lastFailureTime = Instant.now();

            // if state is HALF_OPEN, we need to open circuit breaker
            if (state == State.HALF_OPEN) {
                openCircuit();
            // if failure count is greater than failure threshold
            } else if (failureCount >= failureThreshold) {
                openCircuit();
            }
            // if state is OPEN, we need to return fallback
            return fallback.get();
        }
    }

    private void closeCircuit() {
        state = State.CLOSED;
        failureCount = 0;
        successCount = 0;
    }

    private void openCircuit() {
        state = State.OPEN;
        successCount = 0;
        System.out.println("Circuit is open");
    }

    @FunctionalInterface
    public interface SupplierWithException<T> {
        T get() throws Exception;
    }
}
