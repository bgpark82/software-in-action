package com.bgpark.config.decorator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodDecoratorTest {

    @Test
    void name() {
        Food pizza = new Pizza();
        Food pizzaWithCheese = new CheeseDecorator(pizza);
        Food pizzaWithTomato = new TomatoDecorator(pizzaWithCheese);
        assertEquals(pizzaWithTomato.prepare(), "Pizza with extra cheese with tomato");
    }
}