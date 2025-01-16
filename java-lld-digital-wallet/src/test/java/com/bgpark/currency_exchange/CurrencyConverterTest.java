package com.bgpark.currency_exchange;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyConverterTest {

    @Test
    void convertGBPtoEURWithFixed() throws IOException, InterruptedException {
        CurrencyExchangeRateProvider provider = new FixedCurrencyExchangeRateProvider();
        CurrencyConverter converter = new CurrencyConverter(provider);
        BigDecimal result = converter.convert(Currency.GBP, Currency.EUR, new BigDecimal("1000.00"));
        assertEquals(result, new BigDecimal("1259.74"));
    }

    @Test
    void convertGBPtoEURWithRealTime() throws IOException, InterruptedException {
        CurrencyExchangeRateProvider provider = new RealTimeCurrencyExchangeRateProvider();
        CurrencyConverter converter = new CurrencyConverter(provider);
        BigDecimal result = converter.convert(Currency.GBP, Currency.EUR, new BigDecimal("1000.00"));
        assertEquals(result, new BigDecimal("1259.74"));
    }

    @Test
    void name() {
        BigDecimal divide = new BigDecimal("0.97").divide(new BigDecimal("0.77"), 2, RoundingMode.HALF_UP);
        System.out.println(divide);
    }
}