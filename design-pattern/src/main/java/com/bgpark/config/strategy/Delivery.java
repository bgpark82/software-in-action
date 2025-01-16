package com.bgpark.config.strategy;

/**
 * Strategy
 * - Choose strategy dynamically
 */
public class Delivery {

    private DeliveryStrategy deliveryStrategy;

    // Inject strategy dynamically
    public Delivery(DeliveryStrategy deliveryStrategy) {
        this.deliveryStrategy = deliveryStrategy;
    }

    public void deliver(String order) {
        this.deliveryStrategy.deliver(order);
    }
}
