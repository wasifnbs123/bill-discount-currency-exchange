package com.apis.currencyexchange.service;

import com.apis.currencyexchange.dto.Item;
import com.apis.currencyexchange.model.UserType;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BillDiscountService {
  public double applyDiscount(List<Item> items, UserType userType, int customerTenure) {

    // Total bill
    double totalBill = items.stream().mapToDouble(Item::getPrice).sum();

    // Non-Grocery items total bill
    double nonGroceryItems =
        items.stream()
            .filter(i -> !i.getCategory().equalsIgnoreCase("GROCERY"))
            .mapToDouble(Item::getPrice)
            .sum();

    // Discount percentage calculation
    double percentageDiscount;
    if (userType == UserType.EMPLOYEE) {
      percentageDiscount = 0.30;
    } else if (userType == UserType.AFFILIATE) {
      percentageDiscount = 0.10;
    } else if (userType == UserType.CUSTOMER) {
      if (customerTenure > 2) {
        percentageDiscount = 0.05;
      } else {
        percentageDiscount = 0.0;
      }
    } else {
      percentageDiscount = 0.0; // Default case if userType is none of the above
    }

    // This calculates a flat discount of $5 for every $100 spent.
    double fixedDiscount = ((int) (totalBill / 100)) * 5;

    // Final discount applied total bill
    return totalBill - (nonGroceryItems * percentageDiscount) - fixedDiscount;
  }
}
