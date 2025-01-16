package com.bgpark.curruncy_exchange_v2;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ConversionRateProvider {

    private final Map<Currency, ConversionRate> conversionMap = new ConcurrentHashMap<>();

    public ConversionRateProvider() {
        this.conversionMap.put(Currency.GBP, new ConversionRate("0.78"));
        this.conversionMap.put(Currency.EUR, new ConversionRate("0.94"));
        this.conversionMap.put(Currency.USD, new ConversionRate("1.00"));
    }

    public ConversionRate getConversionRate(Currency from, Currency to) {
        checkCurrency(from, conversionMap);
        checkCurrency(to, conversionMap);

        ConversionRate sourceRate = conversionMap.get(from);
        ConversionRate targetRate = conversionMap.get(to);
        return targetRate.divide(sourceRate);
    }

    /**
     * Immutable map
     */
    public Map<Currency, ConversionRate> getConversionRates() {
        return Collections.unmodifiableMap(conversionMap);
    }

    public void updateExchangeRate(Currency currency, ConversionRate rate) {
        this.updateExchangeRateV2(currency, rate);
    }

    /**
     * put
     * - atomic operation
     */
    public void updateExchangeRateV1(Currency currency, ConversionRate rate) {
        conversionMap.put(currency, rate);
    }
    /**
     * update rate
     * - merge method is atomic operation
     * - if specified key is not there, associates it with the given value
     * - otherwise replace with new value
     * - if it return null, remove the key
     */
    public void updateExchangeRateV2(Currency currency, ConversionRate rate) {
        conversionMap.merge(currency, rate, (oldRate, newRate) -> newRate);
    }

    /**
     * Copy and Write
     * - it's useful if there is read heavy operation
     * - because it doens't need to create unmodifiableMap everytime it reads
     */
    public void updateExchangeRateV3(Currency currency, ConversionRate rate) {
        // because it was unmodifiableMap
        ConcurrentHashMap<Currency, ConversionRate> newMap = new ConcurrentHashMap<>(conversionMap);
        newMap.put(currency, rate);
        // this.conversionMap = Collections.unmodifiableMap(newMap);
    }

    private static void checkCurrency(Currency currency, Map<Currency, ConversionRate> conversionMap) {
        if (!conversionMap.containsKey(currency)) {
            throw new IllegalArgumentException("Invalid currency: %s".formatted(currency));
        }
    }
}
