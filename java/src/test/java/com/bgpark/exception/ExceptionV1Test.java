package com.bgpark.exception;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionV1Test {

    @Test
    void name() throws InterruptedException {
        ExceptionV1.SharedResource s = new ExceptionV1.SharedResource();

        Runnable task = (() -> {
            Optional<Integer> next = s.getNext();
            next.ifPresent(v -> System.out.println(v));
        });

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(task);
        executor.submit(task);
        executor.shutdown();
        Thread.sleep(2000);
    }

    @Test
    void name_v2() throws InterruptedException {
        ExceptionV1.SharedResource s = new ExceptionV1.SharedResource();

        Runnable task = (() -> {
            int next = s.getNextV2();
            if (next != -1) {
                System.out.println("데이터 존재");
            }
        });

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(task);
        executor.submit(task);
        executor.shutdown();
        Thread.sleep(2000);
    }
}