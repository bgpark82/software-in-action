package com.bgpark.config.decorator;

public class CheeseDecorator extends FoodDecorator {

    public CheeseDecorator(Food food) {
        super(food);
    }

    @Override
    public String prepare() {
        return super.prepare() + " with extra cheese";
    }
}
