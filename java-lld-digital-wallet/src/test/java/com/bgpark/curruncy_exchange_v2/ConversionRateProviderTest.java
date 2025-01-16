package com.bgpark.curruncy_exchange_v2;

import com.fasterxml.jackson.core.JsonToken;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ConversionRateProviderTest {

    @Test
    void rate_from_GBP_to_USD() {
        ConversionRateProvider provider = new ConversionRateProvider();
        ConversionRate conversionRate = provider.getConversionRate(Currency.GBP, Currency.USD);
        assertEquals(conversionRate, new ConversionRate("1.28"));
    }

    @Test
    void update_v2() throws InterruptedException {
        ConversionRateProvider provider = new ConversionRateProvider();

        ExecutorService executor = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            executor.submit(() -> provider.updateExchangeRate(Currency.USD, new ConversionRate(String.valueOf(finalI))));
        }

        executor.shutdown();
        while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {

        }
        Map<Currency, ConversionRate> conversionRates = provider.getConversionRates();
        System.out.println(conversionRates.get(Currency.USD).getAmount());;

    }
}