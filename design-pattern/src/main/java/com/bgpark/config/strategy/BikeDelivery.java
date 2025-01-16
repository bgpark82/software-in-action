package com.bgpark.config.strategy;

public class BikeDelivery implements DeliveryStrategy{

    @Override
    public void deliver(String order) {
        System.out.println("Delivering %s via Bike".formatted(order));
    }
}
