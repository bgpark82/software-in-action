package com.bgpark.config.observer;

public class Customer implements Observer{

    @Override
    public void notify(String status) {
        System.out.println("Order status changed = %s".formatted(status));
    }
}
