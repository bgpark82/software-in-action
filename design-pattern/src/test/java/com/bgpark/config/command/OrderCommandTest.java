package com.bgpark.config.command;

import com.bgpark.config.observer.Customer;
import com.bgpark.config.observer.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderCommandTest {

    @Test
    void name() {
        Order order = new Order();
        order.addObservers(new Customer());

        OrderCommand orderCommand = new OrderCommand(order);
        order.setCommand(orderCommand);

        order.processOrder();

    }
}