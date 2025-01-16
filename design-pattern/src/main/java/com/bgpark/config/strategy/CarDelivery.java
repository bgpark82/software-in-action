package com.bgpark.config.strategy;

public class CarDelivery implements DeliveryStrategy{

    @Override
    public void deliver(String order) {
        System.out.println("Delivering %s via Car".formatted(order));
    }
}
