package com.bgpark.config.builder;

public class FoodOrderV1 {

    public static void main(String[] args) {
        FoodOrderV1 foodOrderV1 = new Builder()
                .food("ramen")
                .size(1)
                .build();
    }

    private String food;
    private int size;

    public FoodOrderV1(Builder builder) {
        this.food = builder.food;
        this.size = builder.size;
    }

    static class Builder {
        private String food;
        private int size;

        public Builder() {
        }

        public Builder food(String food) {
            this.food = food;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public FoodOrderV1 build() {
            return new FoodOrderV1(this);
        }
    }
}
