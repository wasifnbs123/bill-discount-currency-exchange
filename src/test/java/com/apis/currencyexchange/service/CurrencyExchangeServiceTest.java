package com.apis.currencyexchange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class CurrencyExchangeServiceTest {

  @InjectMocks private CurrencyExchangeService currencyService;

  @Mock private RestTemplate restTemplate;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testConvertSuccess() {
    double mockRate = 0.9;
    double amount = 100.0;
    double expected = amount * mockRate;

    Map<String, Object> response = new HashMap<>();
    response.put("result", "success");

    Map<String, Double> rates = new HashMap<>();
    rates.put("EUR", mockRate);
    response.put("conversion_rates", rates);

    when(restTemplate.getForObject(contains("USD"), eq(Map.class))).thenReturn(response);

    // Act
    double result = currencyService.convert(amount, "USD", "EUR");

    // Assert
    assertEquals(expected, result);
  }

  @Test
  void testInvalidCurrencyThrowsException() {
    Map<String, Object> mockResponse =
        Map.of("result", "success", "conversion_rates", Map.of("INR", 83.5));

    when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);
    assertThrows(IllegalArgumentException.class, () -> currencyService.convert(100, "USD", "XXX"));
  }
}
