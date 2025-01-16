package com.bgpark.config.factory;

import java.math.BigDecimal;

public class PayPal implements Payment {

    @Override
    public void pay(BigDecimal amount) {
        System.out.println("Paid %s using %s".formatted(amount, this.getClass().getSimpleName()));
    }
}
