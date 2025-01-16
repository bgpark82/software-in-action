package com.bgpark.curruncy_exchange_v2;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DynamicConversionRateProvider {

    private static final int TTL = 1000 * 3;

    private final ConversionRateService conversionRateService;
    private final Map<Currency, BigDecimal> rateMap = new ConcurrentHashMap<>();
    private final Map<Currency, RateCache> rateMapV1 = new ConcurrentHashMap<>();

    public DynamicConversionRateProvider(ConversionRateService conversionRateService) {
        this.conversionRateService = conversionRateService;
    }

    /**
     * use locking mechanism
     * if currency exist, return existing rate
     * if not, it needs to obtain lock and fetch new data
     * Lock need whenever shared resource should be updated
     */
    public BigDecimal getExchangeRateV1(Currency from, Currency to) {
        // atomic
        if (rateMap.containsKey(from)) {
            return rateMap.get(from);
        }

        // if no data, we update it
        synchronized (this) {
            BigDecimal rate = conversionRateService.fetchExchangeRateV1(from, to);
            rateMap.put(from, rate);
            return rate;
        }
    }

    /**
     * Atomic operation
     */
    public BigDecimal getExchangeRateV2(Currency from, Currency to) {
        return rateMap.computeIfAbsent(from, (key) -> conversionRateService.fetchExchangeRateV1(key, to));
    }

    /**
     * TTL
     */
    public BigDecimal getExchangeRateV3(Currency from, Currency to) {
        if (rateMapV1.containsKey(from)) {
            RateCache cache = rateMapV1.get(from);
            if (cache.isExpired()) {
                BigDecimal rate = conversionRateService.fetchExchangeRateV1(from, to);
                rateMapV1.put(from, new RateCache(rate));
                return rate;
            } else {
                return cache.getRate();
            }
        }
        BigDecimal rate = conversionRateService.fetchExchangeRateV1(from, to);
        rateMapV1.put(from, new RateCache(rate));
        return rate;
    }

    /**
     * Bulk
     */
    public BigDecimal getExchangeRateV4(List<Currency> targets, Currency to) {
        ExecutorService executore = Executors.newFixedThreadPool(10);

        Map<Currency, BigDecimal> collect = targets.stream()
                .map(target -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return Map.entry(target, conversionRateService.fetchExchangeRateV1(target, to));
                    } catch (Exception e) {
                        System.out.println("Failed to fetch: send default value");
                        return Map.entry(target, BigDecimal.ZERO);
                    }
                }, executore))
                .map(CompletableFuture::join)
                .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()));

        return BigDecimal.ZERO;
    }

    public BigDecimal getExchangeRateV5(List<Currency> targets, Currency to) {
        Map<Currency, BigDecimal> collect = targets.parallelStream()
                .map(target -> {
                    try {
                        return Map.entry(target, conversionRateService.fetchExchangeRateV1(target, to));
                    } catch (Exception e) {
                        System.out.println("Failed to fetch: send default value");
                        return Map.entry(target, BigDecimal.ZERO);
                    }
                })
                .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()));

        return BigDecimal.ZERO;
    }

    static class RateCache {
        private final BigDecimal rate;
        private final long timestamp;

        public RateCache(BigDecimal rate) {
            this.rate = rate;
            this.timestamp = System.currentTimeMillis();
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - this.timestamp > TTL;
        }

        public BigDecimal getRate() {
            return rate;
        }
    }
}
