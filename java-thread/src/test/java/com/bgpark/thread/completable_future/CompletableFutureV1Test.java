package com.bgpark.thread.completable_future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureV1Test {

    @Test
    void basic_completable_future() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello, World!";
        });

        future.thenAccept(result -> {
            System.out.println(result);
        });
    }

    @Test
    void chaining() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(greet -> greet + " World")
                .thenApply(greet -> greet + "!")
                .thenApply(greet -> greet.toUpperCase());

        future.thenAccept(result -> {
            System.out.println(result);
        });
    }

    @Test
    void chaining_multiple_future() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(greet -> greet + " World")
                .thenApply(greet -> greet + "!")
                .thenApply(greet -> greet.toUpperCase());

        future.thenAccept(result -> {
            System.out.println(result);
        });
    }

    @Test
    void chaining_multiple_future_combine() {
        CompletableFuture<String> futureV1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> futureV2 = CompletableFuture.supplyAsync(() -> "World!");
        CompletableFuture<String> futureV3 = futureV1.thenCombine(futureV2, (s1, s2) -> s1 + " " + s2);
        futureV3.thenAccept(result -> {
            System.out.println(result);
        });
    }

    @Test
    void compose() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(greet -> CompletableFuture.supplyAsync(() -> greet + " World"))
                .thenCompose(greet -> CompletableFuture.supplyAsync(() -> greet + "!"))
                .thenCompose(greet -> CompletableFuture.supplyAsync(() -> greet.toUpperCase()));

        future.thenAccept(result -> {
            System.out.println(result);
        });
    }

    /**
     * Exception handling
     * - exceptionally : handle exception and return default value
     */
    @Test
    void exceptionally() {
        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Exception occurred");
        }).exceptionally(ex -> {
            return "Hello, World!";
        });

        future.thenAccept(result -> {
            System.out.println(result);
        });
    }

    /**
     * handle : handle exception and return default value
     */
    @Test
    void handle() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Exception occurred");
        }).handle((result, ex) -> {
            return "Hello, World!";
        });

        future.thenAccept(result -> {
            System.out.println(result);
        });
    }

    /**
     * whenComplete : handle exception and return default value without alerting any outcome
     */
    @Test
    void whenComplete() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Exception occurred");
        });

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("Exception occurred");
            } else {
                System.out.println(result);
            }
        });
    }

    /**
     * join : get result without throwing exceptino
     */
    @Test
    void join() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                throw new RuntimeException("Exception occurred");
            } catch (Exception e) {
                return "Hello, World!";
            }

        });
        String result = future.join();
        assertEquals("Hello, World!", result);
    }

    @Test
    void get() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                throw new RuntimeException("Exception occurred");
            } catch (Exception e) {
                return "Hello, World!";
            }

        });
        String result = future.get();
        System.out.println(result);
        assertEquals("Hello, World!", result);
    }

    @Test
    void allOf() throws InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100L);
                return "!";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);

        allOf.thenRun(() -> {
            try {
                String result1 = future1.get();
                String result2 = future2.get();
                String result3 = future3.get();
                System.out.println(result1 + " " + result2 + result3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(1000);
    }

    @Test
    void anyOf() throws InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "World";
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "!";
        });
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future1, future2, future3);

        anyOf.thenAccept(result -> {
            System.out.println("hello");
            System.out.println(result);
        });

        Thread.sleep(1000);
    }
}