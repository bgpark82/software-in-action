package com.bgpark.config.decorator;

abstract class FoodDecorator implements Food {

    protected Food food;

    public FoodDecorator(Food food) {
        this.food = food;
    }

    @Override
    public String prepare() {
        return food.prepare();
    }
}
