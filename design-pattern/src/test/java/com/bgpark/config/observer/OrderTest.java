package com.bgpark.config.observer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void name() {
        Customer c1 = new Customer();
        Customer c2 = new Customer();
        Customer c3 = new Customer();
        Order order = new Order();
        order.addObservers(c1);
        order.addObservers(c2);
        order.addObservers(c3);
        order.setStatus("DELIVERED");
    }
}