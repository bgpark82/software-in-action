package com.bgpark.currency_exchange;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class FixedCurrencyExchangeRateProviderTest {

    @Test
    void name() throws InterruptedException {
        FixedCurrencyExchangeRateProvider exchangeRateProvider = new FixedCurrencyExchangeRateProvider();

        ExecutorService executorService = Executors.newFixedThreadPool(1_000);
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            executorService.submit(() -> exchangeRateProvider.updateExchangeRateV4(Currency.USD, new BigDecimal(finalI)));
        }
        executorService.shutdown();
        while (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("Waiting for termination");
        }
        assertEquals(exchangeRateProvider.getExchangeRates().get(Currency.USD), new BigDecimal(9999));
    }
}