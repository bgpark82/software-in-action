package com.bgpark.currency_exchange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Currency Exchange
 * 1. Currency
 *  - USD (Key Currency), GBP, EUR
 *
 * 2. Currency Converter
 *  - convert from source to target with money
 *
 * 3. Money
 *  - BigDecimal
 *
 * 4. Currency Exchange
 *  - it has source and target currency
 *
 */


public class CurrencyExchange {

    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    static class CurrencyConverter {

        private static Map<Currency, BigDecimal> conversionMap = new ConcurrentHashMap<>();
        static {
            conversionMap.put(Currency.GBP, new BigDecimal("0.77"));
            conversionMap.put(Currency.EUR, new BigDecimal("0.97"));
        }

        public BigDecimal convert(Currency source, Currency target, BigDecimal amount) {
            // amount(USD) * target(GBP)/amount(USD) = GBP
            // amount == null
            // amount == 0
            // amount < 0
            // source == target
            // TODO: Use default value
            if (Objects.isNull(amount)) {
                throw new BusinessException("Invalid amount: %s".formatted(amount));
            }

            if (amount.compareTo(ZERO) == 0) {
                return ZERO;
            }

            if (amount.compareTo(ZERO) < 1) {
                throw new BusinessException("Invalid negative amount: %s".formatted(amount));
            }

            return amount
                    .multiply(conversionMap.get(target))
                    .divide(conversionMap.get(source))
                    .setScale(2, RoundingMode.HALF_UP);
        }
    }

    static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }

    enum Currency {
        USD, GBP, EUR
    }
}
