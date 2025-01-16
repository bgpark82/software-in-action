package com.bgpark.curruncy_exchange_v2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Convert currency
 * - Example: 100 GBP to EUR
 * - USD as key currency
 */
public class CurrencyConverter {

    private final ConversionRateProvider conversionRateProvider;

    public CurrencyConverter(ConversionRateProvider conversionRateProvider) {
        this.conversionRateProvider = conversionRateProvider;
    }

    /**
     * USD/USD = 1
     * GBP/USD = 0.78
     * EUR/USD = 0.94
     *
     * from GBP to EUR
     * 1000 GBP * EUR/USD * USD/GBP = X EUR
     *
     * @param amount: BigDecimal with String
     * @param from
     * @param to
     */
    public ConversionRate convert(ConversionRate amount, Currency from, Currency to) {
        ConversionRate conversionRate = conversionRateProvider.getConversionRate(from, to);

        return amount.multiply(conversionRate);
    }

    public void update() {

    }

    private static void checkCurrency(Currency currency, Map<Currency, ConversionRate> conversionMap) {
        if (!conversionMap.containsKey(currency)) {
            throw new IllegalArgumentException("Invalid currency: %s".formatted(currency));
        }
    }
}
