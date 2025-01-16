package com.bgpark.config.decorator.v1;

public class CheeseDecoratorV1 extends FoodDecoratorV1{

    public CheeseDecoratorV1(FoodV1 food) {
        super(food);
    }

    @Override
    public String prepare() {
        return super.prepare() + " with cheese";
    }
}
