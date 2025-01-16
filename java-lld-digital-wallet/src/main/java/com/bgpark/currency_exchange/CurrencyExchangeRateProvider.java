package com.bgpark.currency_exchange;

import java.io.IOException;
import java.math.BigDecimal;

public interface CurrencyExchangeRateProvider {

    BigDecimal getExchangeRate(Currency source, Currency target) throws IOException, InterruptedException;
}
