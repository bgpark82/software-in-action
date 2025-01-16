package com.bgpark.currency_exchange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

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


public class FixedCurrencyExchangeRateProvider implements CurrencyExchangeRateProvider {

    private static final int SCALE = 5;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(SCALE, ROUNDING_MODE);

    private static Map<Currency, BigDecimal> conversionMap = new ConcurrentHashMap<>();
    static {
        conversionMap.put(Currency.USD, new BigDecimal("1.00"));
        conversionMap.put(Currency.GBP, new BigDecimal("0.77"));
        conversionMap.put(Currency.EUR, new BigDecimal("0.97"));
    }

    @Override
    public BigDecimal getExchangeRate(Currency source, Currency target) {
        // source: GBP
        // target: EUR
        // EUR/GBP = (EUR/USD) * (USD/GBP)
        BigDecimal sourceRate = conversionMap.getOrDefault(source, new BigDecimal("1.00"));
        BigDecimal targetRate = conversionMap.getOrDefault(target, new BigDecimal("1.00"));
        return targetRate.divide(sourceRate, SCALE, ROUNDING_MODE);
    }

    /**
     * get exchange rates
     */
    public Map<Currency, BigDecimal> getExchangeRates() {
        return Collections.unmodifiableMap(conversionMap);
    }

    /**
     * update exchange rate V1
     * - It makes sure that the rate is updated atomically
     * - but it is not thread safe because it is not synchronized
     * - It can be updated by multiple threads at the same time
     * - race condition can occur
     *
     * - without synchronized keyword, it can be updated by multiple threads at the same time
     * - because concurrencyMap is atomic for single get and put operation, but not for sequential operations
     * - so it can be updated by multiple threads at the same time
     */
    public synchronized void updateExchangeRateV1(Currency currency, BigDecimal rate) {
        System.out.println("rate=" + rate);
        conversionMap.put(currency, rate);
    }

    /**
     * update exchange rate V1_1
     * - merge method is atomic operation
     * - it doesn't need synchronized keyword
     */
    public void updateExchangeRateV1_1(Currency currency, BigDecimal rate) {
        System.out.println("rate=" + rate);
        conversionMap.merge(currency, rate, (oldRate, newRate) -> newRate);
    }

    /**
     * update exchange rate V2
     * - It is thread safe because it is synchronized
     * - but it is not efficient because it is synchronized
     * - It can be updated by only one thread at the same time
     * - race condition can't occur
     */
    ReentrantLock lock = new ReentrantLock(true);
    public void updateExchangeRateV2(Currency currency, BigDecimal rate) {
        lock.lock();
        try {
            System.out.println("rate=" + rate);
            conversionMap.put(currency, rate);
        } finally {
            lock.unlock();
        }
    }

    /**
     * update exchange rate V3
     * - It is thread safe because it is synchronized
     * - It is efficient because it is synchronized
     * - It can be updated by only one thread at the same time
     * - non blocking
     */
    public void updateExchangeRateV3(Currency currency, BigDecimal rate) {
        if (lock.tryLock()) {
            try {
                System.out.println("rate=" + rate);
                conversionMap.put(currency, rate);
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * Copy on Write
     * - it is useful when read heavy operation
     * - conversionMap should be volatile because when thread read conversionMap, it can be read from cache
     * - volatile make sure that the value is read from main memory
     * - So all of thread can read the latest value
     * - synchronized: ensure that only one thread can access the method at the same time
     * - But performance is not good because it is synchronized
     */
    public void updateExchangeRateV4(Currency currency, BigDecimal rate) {
        Map<Currency, BigDecimal> newMap = new ConcurrentHashMap<>(conversionMap);
        newMap.put(currency, rate);
        this.conversionMap = Collections.unmodifiableMap(newMap);
    }
}
