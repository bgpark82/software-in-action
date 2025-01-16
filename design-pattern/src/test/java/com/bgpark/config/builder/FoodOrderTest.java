package com.bgpark.config.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodOrderTest {

    @Test
    void name() {
        FoodOrder pizza = new FoodOrder.Builder()
                .food("pizza")
                .size(2)
                .topping("tomato")
                .extraCheese(true)
                .build();
        assertEquals(pizza.getFood(), "pizza");
    }
}