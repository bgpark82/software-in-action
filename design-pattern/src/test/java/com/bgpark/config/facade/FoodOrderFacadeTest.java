package com.bgpark.config.facade;

import com.bgpark.config.strategy.BikeDelivery;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class FoodOrderFacadeTest {

    @Test
    void name() {
        FoodOrderFacade facade = new FoodOrderFacade(new BikeDelivery());
        facade.order("paypal", BigDecimal.valueOf(1000L));
    }
}