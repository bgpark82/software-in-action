package com.bgpark.thread;

public class Reorder {

    private int a = 0;
    private boolean flag = false;  // No volatile keyword - potential for reordering issues

    public static void main(String[] args) throws InterruptedException {
        Reorder example = new Reorder();

        // Start the writer thread
        Thread writerThread = new Thread(example::writer, "Writer-Thread");
        writerThread.start();

        // Start multiple reader threads
        Thread readerThread1 = new Thread(example::reader, "Reader-Thread-1");
        Thread readerThread2 = new Thread(example::reader, "Reader-Thread-2");

        readerThread1.start();
        readerThread2.start();

        // Wait for all threads to complete
        writerThread.join();
        readerThread1.join();
        readerThread2.join();
    }

    // Writer method
    public void writer() {
        System.out.println(Thread.currentThread().getName() + " is writing...");
        a = 1;          // Step 1: Set a to 1
        flag = true;    // Step 2: Set flag to true
        System.out.println(Thread.currentThread().getName() + " has finished writing.");
    }

    // Reader method
    public void reader() {
        while (!flag) {
            // Busy-wait until flag becomes true
        }
        System.out.println(Thread.currentThread().getName() + " sees a = " + a);
    }
}
