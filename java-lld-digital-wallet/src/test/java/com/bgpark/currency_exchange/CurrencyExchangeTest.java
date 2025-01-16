package com.bgpark.currency_exchange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static com.bgpark.currency_exchange.CurrencyConverter.*;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyExchangeTest {

    private CurrencyConverter currencyConverter;

    @BeforeEach
    void setUp() {
        FixedCurrencyExchangeRateProvider exchangeRateProvider = new FixedCurrencyExchangeRateProvider();
        currencyConverter = new CurrencyConverter(exchangeRateProvider);
    }

    @Test
    void convert() throws IOException, InterruptedException {
        BigDecimal converted = currencyConverter.convert(Currency.USD, Currency.GBP, BigDecimal.valueOf(1000L));
        assertEquals(converted, BigDecimal.valueOf(770L).setScale(2));
    }

    @Test
    void convert_notAllowNullAmount() {
        BusinessException exception = assertThrows(BusinessException.class, () -> currencyConverter.convert(Currency.USD, Currency.GBP, null));
        assertEquals(exception.getMessage(), "Invalid amount: null");
    }

    @Test
    void convert_handleZeroAmount() throws IOException, InterruptedException {
        BigDecimal converted = currencyConverter.convert(Currency.USD, Currency.GBP, BigDecimal.ZERO);
        assertEquals(converted, BigDecimal.ZERO);
    }

    @Test
    void convert_notAllowNegativeAmount() {
        BusinessException exception = assertThrows(BusinessException.class, () -> currencyConverter.convert(Currency.USD, Currency.GBP, BigDecimal.valueOf(-1)));
        assertEquals(exception.getMessage(), "Invalid negative amount: -1");
    }
}