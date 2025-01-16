package com.bgpark.config.command;

import com.bgpark.config.observer.Order;

public class OrderCommandV1 {

    private final Order order;

    public OrderCommandV1(Order order) {
        this.order = order;
    }

    public void order() {
        order.setStatus("ORDERED");
    }

    public void deliver() {
        order.setStatus("DELIVERED");
    }
}
