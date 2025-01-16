package com.bgpark.config.command;

import com.bgpark.config.observer.Order;

/**
 * Command
 * - Encapsulate request
 */
public class OrderCommand implements Command {

    private Order order;

    public OrderCommand(Order order) {
        this.order = order;
    }

    @Override
    public void execute() {
        order.setStatus("ORDERED");
    }
}
