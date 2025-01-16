package com.bgpark.config.decorator;

public class TomatoDecorator extends FoodDecorator {

    public TomatoDecorator(Food food) {
        super(food);
    }

    @Override
    public String prepare() {
        return super.prepare() + " with tomato";
    }
}
