package com.bgpark.curruncy_exchange_v2;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyConverterTest {

    private final Map<String, String> map = new HashMap<>();
    private final ConversionRateProvider conversionRateProvider = new ConversionRateProvider();

    @Test
    void convert_from_USD_to_GBP() {
        CurrencyConverter converter = new CurrencyConverter(conversionRateProvider);
        ConversionRate result = converter.convert(new ConversionRate("1000"), Currency.USD, Currency.GBP);
        assertEquals(result, new ConversionRate("780"));
    }

    @Test
    void convert_from_USD_to_EUR() {
        CurrencyConverter converter = new CurrencyConverter(conversionRateProvider);
        ConversionRate result = converter.convert(new ConversionRate("1000"), Currency.USD, Currency.EUR);
        assertEquals(result, new ConversionRate("940"));
    }

    @Test
    void convert_from_USD_to_USD() {
        CurrencyConverter converter = new CurrencyConverter(conversionRateProvider);
        ConversionRate result = converter.convert(new ConversionRate("1000"), Currency.USD, Currency.USD);
        assertEquals(result, new ConversionRate("1000"));
    }
}