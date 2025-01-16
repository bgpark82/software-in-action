package com.bgpark.config.strategy;

import org.junit.jupiter.api.Test;

class DeliveryTest {

    @Test
    void name() {
        DeliveryStrategy bikeDelivery = new BikeDelivery();
        CarDelivery carDelivery = new CarDelivery();

        Delivery delivery = new Delivery(bikeDelivery);
        delivery.deliver("Food");

        Delivery delivery1 = new Delivery(carDelivery);
        delivery1.deliver("Box");
    }
}