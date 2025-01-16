package com.bgpark.currency_exchange;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class RealTimeCurrencyExchangeRateProvider implements CurrencyExchangeRateProvider {

    private static final String API_URL = "https://api.exchangerate.host/latest";
    private static final long CACHE_EXPIRATION_MS = 60 * 60 * 1000; // 1 hour

    private final Map<Currency, BigDecimal> conversionMapV1 = new ConcurrentHashMap<>();
    private final Map<Currency, ExchangeRateCache> conversionMapV2 = new ConcurrentHashMap<>();

    /**
     * External API call to get exchange rate
     * caching to avoid frequent calls
     * handling exceptions that may occur during API call
     */
    @Override
    public BigDecimal getExchangeRate(Currency source, Currency target) throws IOException, InterruptedException {
        if (conversionMapV1.containsKey(source)) {
            return conversionMapV1.get(source);
        }

        BigDecimal rate = fetchExchangeRateV1(source, target);
        conversionMapV1.put(source, rate);
        return rate;
    }

    /**
     * Cache API call
     */
    private BigDecimal getExchangeRateV1(Currency source, Currency target) {
        if (conversionMapV1.containsKey(source)) {
            return conversionMapV1.get(source);
        }

        BigDecimal rate = fetchExchangeRateV1(source, target);
        conversionMapV1.put(source, rate);
        return rate;
    }

    /**
     * Cache API call with TTL
     */
    private BigDecimal getExchangeRateV2(Currency source, Currency target) {
        ExchangeRateCache cache = conversionMapV2.get(source);
        if (conversionMapV2.containsKey(source) && !cache.isExpired()) {
            return cache.getRate();
        }

        BigDecimal rate = fetchExchangeRateV1(source, target);
        conversionMapV2.put(source, new ExchangeRateCache(source, rate));
        return rate;
    }

    /**
     * Request Bulk API
     */
    private Map<Currency, BigDecimal> fetchExchangeRateV2(Currency source, List<Currency> targets) {
        HashMap<Currency, BigDecimal> exchangeRate = new HashMap<>();

        for (Currency target : targets) {
            exchangeRate.put(target, fetchExchangeRateV1(source, target));
        }
        return exchangeRate;
    }

    /**
     * Request Bulk API with CompletableFuture
     * For exception handling, we don't need to use exceptionally or handle,
     * because we are handling exception in fetchExchangeRateV1 method
     */
    private Map<Currency, BigDecimal> fetchExchangeRateV3(Currency source, List<Currency> targets) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<CompletableFuture<Map.Entry<Currency, BigDecimal>>> futures = targets.stream()
                .map(target -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return Map.entry(target, fetchExchangeRateV1(source, target));
                    } catch (Exception e) {
                        System.out.println("Failed to fetch rate");
                        // return default value
                        return Map.entry(target, BigDecimal.ZERO);
                    }

                }, executor))
                .collect(Collectors.toList());

        return futures.stream()
                // join won't throw exception
                .map(CompletableFuture::join)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private BigDecimal fetchExchangeRateV1(Currency source, Currency target) {
        String url = API_URL + "?base=" + source.name() + "&symbols=" + target.name();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return parseExchangeRate(response.body(), target.name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private BigDecimal parseExchangeRate(String responseBody, String targetCurrency) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(responseBody);
        JsonNode ratesNode = jsonNode.get("rates");
        return ratesNode != null ? ratesNode.get(targetCurrency).decimalValue() : BigDecimal.ZERO;
    }

    private static class ExchangeRateCache {
        private final Currency currency;
        private final BigDecimal rate;
        private final long timestamp;

        public ExchangeRateCache(Currency currency, BigDecimal rate) {
            this.currency = currency;
            this.rate = rate;
            this.timestamp = System.currentTimeMillis();
        }

        public BigDecimal getRate() {
            return rate;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_EXPIRATION_MS;
        }
    }
}
