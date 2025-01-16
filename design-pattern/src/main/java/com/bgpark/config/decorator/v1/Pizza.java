package com.bgpark.config.decorator.v1;

import com.bgpark.config.decorator.Food;

public class Pizza implements FoodV1 {

    @Override
    public String prepare() {
        return "Pizza";
    }

    public static void main(String[] args) {
        Pizza pizza = new Pizza();
        FoodV1 cheeseDecoratorV1 = new CheeseDecoratorV1(pizza);
        System.out.println(cheeseDecoratorV1.prepare());;
    }
}
