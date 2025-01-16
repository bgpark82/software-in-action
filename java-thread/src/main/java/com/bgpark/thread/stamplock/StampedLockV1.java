package com.bgpark.thread.stamplock;

import java.util.concurrent.locks.StampedLock;

public class StampedLockV1 {

    private StampedLock lock = new StampedLock();
    private int balance = 0;

    public void deposit(int amount) {
        // multi thread write data exclusively
        // it means only one thread can modify data
        long stamp = lock.writeLock();
        System.out.println(stamp);
        try {
            this.balance += amount;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    // but multiple thread can read with optimistic lock
    // but validation still required because the read data can be changed while other thread update data
    public int getBalance() {
        // if it's zero, it means exclusive lock
        // it will return the snapshot of state (non blocking)
        // it doesn't acquire actual lock
        // optimistic lock allow other thread to modify balance concurrently while reading it
        long stamp = lock.tryOptimisticRead();
        int currentBalance = balance;
        // check data has been modified
        // it means other thread update data
        // if it's valid, it means balance doesn't changed, it will return true
        //
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                currentBalance = balance;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return currentBalance;
    }
}
