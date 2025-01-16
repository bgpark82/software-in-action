package com.bgpark.curruncy_exchange_v2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class ConversionRate {

    private static final int SCALE = 2;
    private static final RoundingMode ROUND_MODE = RoundingMode.HALF_UP;

    private final BigDecimal amount;

    public ConversionRate(String amount) {
        this.amount = new BigDecimal(amount).setScale(SCALE, ROUND_MODE);
    }

    public ConversionRate(BigDecimal amount) {
        this.amount = amount.setScale(SCALE, ROUND_MODE);
    }

    public ConversionRate add(ConversionRate conversionRate) {
        return new ConversionRate(this.amount.add(conversionRate.getAmount()).setScale(SCALE, ROUND_MODE));
    }

    public ConversionRate multiply(ConversionRate conversionRate) {
        return new ConversionRate(this.amount.multiply(conversionRate.getAmount()).setScale(SCALE, ROUND_MODE));
    }

    public ConversionRate divide(ConversionRate conversionRate) {
        return new ConversionRate(this.amount.divide(conversionRate.getAmount(), SCALE, ROUND_MODE));
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversionRate that = (ConversionRate) o;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
