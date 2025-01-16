package com.bgpark.config.decorator;

public class Pizza implements Food {

    @Override
    public String prepare() {
        return "Pizza";
    }
}
