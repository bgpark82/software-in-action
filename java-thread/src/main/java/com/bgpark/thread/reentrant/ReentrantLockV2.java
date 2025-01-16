package com.bgpark.thread.reentrant;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockV2 {

    private ReentrantLock lock = new ReentrantLock();

    public void perform() {
        try {
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + " acquire lock");
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " release lock");
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockV2 example = new ReentrantLockV2();
        Thread thread1 = new Thread(example::perform);
        Thread thread2 = new Thread(example::perform);
        thread1.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.start();

        try {
            Thread.sleep(500);
            System.out.println("interrupting");
            thread2.interrupt();
        } catch (InterruptedException e) {
            System.out.println("interrupted!");
            e.printStackTrace();
        }
    }
}
