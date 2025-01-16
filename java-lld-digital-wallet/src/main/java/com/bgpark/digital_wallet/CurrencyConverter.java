package com.bgpark.digital_wallet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {

    private static final Map<Account.Currency, BigDecimal> exchangeRate = new HashMap<>();

    static {
        exchangeRate.put(Account.Currency.USD, BigDecimal.ONE);
        exchangeRate.put(Account.Currency.GBP, new BigDecimal("0.72"));
        exchangeRate.put(Account.Currency.EUR, new BigDecimal("0.85"));
    }

    /**
     * 10GBP -> ?USD
     * 10GBP / GBP * USD
     * @param amount
     * @param source
     * @param target
     * @return
     *
     * TODO: static issue??
     */
    public static BigDecimal convert(BigDecimal amount, Account.Currency source, Account.Currency target) {
        BigDecimal sourceRate = exchangeRate.get(source);
        BigDecimal targetRate = exchangeRate.get(target);
        return amount.multiply(targetRate).divide(sourceRate, RoundingMode.HALF_UP);
    }
}
