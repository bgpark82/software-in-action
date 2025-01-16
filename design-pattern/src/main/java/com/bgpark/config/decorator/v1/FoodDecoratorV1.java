package com.bgpark.config.decorator.v1;

abstract class FoodDecoratorV1 implements FoodV1{

    protected FoodV1 food;

    public FoodDecoratorV1(FoodV1 food) {
        this.food = food;
    }

    @Override
    public String prepare() {
        return food.prepare();
    }
}
