package com.apis.currencyexchange.service;

import java.util.Map;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyExchangeService {

  private final RestTemplate restTemplate;

  public CurrencyExchangeService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  private static final String API_URL =
      "https://v6.exchangerate-api.com/v6/733337ec46351e665fcad4ff/latest/";

  @Cacheable("exchangeRates")
  public double convert(double amount, String from, String to) {
    String url = API_URL + from;
    Map<String, Object> response = restTemplate.getForObject(url, Map.class);
    if (!"success".equals(response.get("result"))) {
      throw new RuntimeException("Failed to retrieve exchange rates");
    }
    Map<String, Double> rates = (Map<String, Double>) response.get("conversion_rates");
    if (!rates.containsKey(to)) {
      throw new IllegalArgumentException("Invalid target currency: " + to);
    }
    return amount * rates.get(to);
  }
}
