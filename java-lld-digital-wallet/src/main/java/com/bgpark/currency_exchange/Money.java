package com.bgpark.currency_exchange;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private final BigDecimal amount;

    public Money(String amount) {
        this.amount = new BigDecimal(amount).setScale(SCALE, ROUNDING_MODE);
    }

    public Money(BigDecimal amount) {
        this.amount = amount.setScale(SCALE, ROUNDING_MODE);
    }

    public Money add(Money money) {
        return new Money(this.amount.add(money.amount));
    }
}
