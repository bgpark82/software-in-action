package com.bgpark.curruncy_exchange_v2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {

    private static final int SCALE = 2;
    private static final RoundingMode ROUND_MODE = RoundingMode.HALF_UP;

    private final BigDecimal amount;

    public Money(String amount) {
        this.amount = new BigDecimal(amount).setScale(SCALE, ROUND_MODE);
    }

    public Money(BigDecimal amount) {
        this.amount = amount.setScale(SCALE, ROUND_MODE);
    }

    public Money add(Money money) {
        return new Money(this.amount.add(money.getAmount()).setScale(SCALE, ROUND_MODE));
    }

    public Money multiply(Money money) {
        return new Money(this.amount.multiply(money.getAmount().setScale(SCALE, ROUND_MODE)));
    }

    public Money divide(Money money) {
        return new Money(this.amount.divide(money.getAmount().setScale(SCALE, ROUND_MODE)));
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
