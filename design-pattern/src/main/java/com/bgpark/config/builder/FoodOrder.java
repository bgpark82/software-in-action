package com.bgpark.config.builder;

/**
 * Builder
 * - When class has variables
 */
public class FoodOrder {

    private String food;
    private int size;
    private String topping;
    private boolean extraCheese;

    public FoodOrder(Builder builder) {
        this.food = builder.food;
        this.size = builder.size;
        this.topping = builder.topping;
        this.extraCheese = builder.extraCheese;
    }

    public String getFood() {
        return food;
    }

    public int getSize() {
        return size;
    }

    public String getTopping() {
        return topping;
    }

    public boolean isExtraCheese() {
        return extraCheese;
    }

    public static class Builder {
        private String food;
        private int size;
        private String topping;
        private boolean extraCheese;

        public Builder food(String food) {
            this.food = food;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder topping(String topping) {
            this.topping = topping;
            return this;
        }

        public Builder extraCheese(boolean extraCheese) {
            this.extraCheese = extraCheese;
            return this;
        }

        public FoodOrder build() {
            return new FoodOrder(this);
        }
    }
}
