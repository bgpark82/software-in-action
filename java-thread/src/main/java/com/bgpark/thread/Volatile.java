package com.bgpark.thread;

public class Volatile {

    private static boolean collectingData = true;

    /**
     * visibility between main and worker thread
     */
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            while (collectingData) {
                System.out.println(Thread.currentThread().getName() + " is collecting data...");
            }
            System.out.println(Thread.currentThread().getName() + " has paused data collection ...");
        };

        Thread v1 = new Thread(task);

        v1.start();

        Thread.sleep(3000L);

        pause();
        System.out.println("stopped");

        v1.join();
    }

    private static void pause() {
        collectingData = false;
    }
}
