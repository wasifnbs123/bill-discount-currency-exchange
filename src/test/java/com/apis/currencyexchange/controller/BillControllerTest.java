package com.apis.currencyexchange.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.apis.currencyexchange.dto.Item;
import com.apis.currencyexchange.dto.ItemBillRequest;
import com.apis.currencyexchange.dto.ItemBillResponse;
import com.apis.currencyexchange.service.BillDiscountService;
import com.apis.currencyexchange.service.CurrencyExchangeService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class BillControllerTest {

  @Mock private BillDiscountService discountService;

  @Mock private CurrencyExchangeService currencyService;

  @InjectMocks private BillController billController;

  public BillControllerTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCalculateWithValidRequest() {
    ItemBillRequest request = new ItemBillRequest();
    request.setUserType("EMPLOYEE");
    request.setCustomerTenure(1);
    request.setOriginalCurrency("USD");
    request.setTargetCurrency("EUR");
    request.setItems(
        List.of(new Item("TV", "NON-GROCERY", 1000), new Item("Apple", "GROCERY", 100)));

    when(discountService.applyDiscount(any(), any(), anyInt())).thenReturn(750.0);
    when(currencyService.convert(750.0, "USD", "EUR")).thenReturn(675.0);

    ResponseEntity<ItemBillResponse> response = billController.calculate(request);

    assertNotNull(response.getBody());
    assertEquals(1100, response.getBody().getOriginalBill());
    assertEquals(750, response.getBody().getDiscountedAmount());
    assertEquals(675, response.getBody().getNetPayableAmountInTargetCurrency());
  }
}
