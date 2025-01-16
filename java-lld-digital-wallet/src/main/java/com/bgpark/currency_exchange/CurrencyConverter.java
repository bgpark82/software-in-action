package com.bgpark.currency_exchange;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class CurrencyConverter {

    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    private final CurrencyExchangeRateProvider exchangeRateProvider;

    public CurrencyConverter(CurrencyExchangeRateProvider exchangeRateProvider) {
        this.exchangeRateProvider = exchangeRateProvider;
    }

    public BigDecimal convert(Currency source, Currency target, BigDecimal amount) throws IOException, InterruptedException {
        if (Objects.isNull(amount)) {
            throw new BusinessException("Invalid amount: %s".formatted(amount));
        }

        if (amount.compareTo(ZERO) == 0) {
            return ZERO;
        }

        if (amount.compareTo(ZERO) < 1) {
            throw new BusinessException("Invalid negative amount: %s".formatted(amount));
        }

        BigDecimal exchangeRate = exchangeRateProvider.getExchangeRate(source, target);
        System.out.println(exchangeRate);
        return amount
                .multiply(exchangeRate)
                .setScale(2, RoundingMode.HALF_UP);
    }


    static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }
}
