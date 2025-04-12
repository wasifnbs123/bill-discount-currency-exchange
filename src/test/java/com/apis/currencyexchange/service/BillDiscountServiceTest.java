package com.apis.currencyexchange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.apis.currencyexchange.dto.Item;
import com.apis.currencyexchange.model.UserType;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BillDiscountServiceTest {

  private final BillDiscountService discountService = new BillDiscountService();

  @Test
  void testEmployeeGets30PercentDiscount() {
    List<Item> items =
        List.of(new Item("TV", "NON_GROCERY", 1000), new Item("Apple", "GROCERY", 100));
    double result = discountService.applyDiscount(items, UserType.EMPLOYEE, 1);
    assertEquals(745.0, result);
  }

  @Test
  void testCustomerWithMoreThan2YearsGets5PercentDiscount() {
    List<Item> items = List.of(new Item("Sofa", "NON_GROCERY", 1000));
    double result = discountService.applyDiscount(items, UserType.CUSTOMER, 3);
    assertEquals(1000 - (1000 * 0.05) - 50, result);
  }

  @Test
  void testAffiliateGets10PercentDiscount() {
    List<Item> items = List.of(new Item("Chair", "NON_GROCERY", 200));
    double result = discountService.applyDiscount(items, UserType.AFFILIATE, 0);
    assertEquals(200 - (200 * 0.10) - 10, result);
  }
}
