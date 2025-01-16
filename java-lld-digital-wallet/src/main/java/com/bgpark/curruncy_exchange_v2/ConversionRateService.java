package com.bgpark.curruncy_exchange_v2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ConversionRateService {

    private static final String API_URL = "https://api.exchangerate.host/latest";
    private static final int MAX_RETRY_COUNT = 3;
    /**
     * fetch exchange rate
     * - timeout
     * - retry
     * - retry delay
     */
    public BigDecimal fetchExchangeRateV1(Currency source, Currency target) {
        String url = API_URL + "?base=" + source.name() + "&symbols=" + target.name();
        HttpClient client = HttpClient.newHttpClient();

        int retry = 0;
        while (retry < MAX_RETRY_COUNT) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .build();

            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                return parseExchangeRate(response.body(), target.name());
            } catch (IOException | InterruptedException e) {
                retry++;
                if (retry > MAX_RETRY_COUNT) {
                    throw new RuntimeException(e);
                }
                System.out.println("Attempt fetching API %s times, retrying...".formatted(retry));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        throw new RuntimeException("API fetch failed");
    }

    private BigDecimal parseExchangeRate(String responseBody, String targetCurrency) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(responseBody);
        JsonNode ratesNode = jsonNode.get("rates");
        return ratesNode != null ? ratesNode.get(targetCurrency).decimalValue() : BigDecimal.ZERO;
    }
}
