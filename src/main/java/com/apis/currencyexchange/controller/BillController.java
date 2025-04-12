package com.apis.currencyexchange.controller;

import com.apis.currencyexchange.dto.Item;
import com.apis.currencyexchange.dto.ItemBillRequest;
import com.apis.currencyexchange.dto.ItemBillResponse;
import com.apis.currencyexchange.model.UserType;
import com.apis.currencyexchange.service.BillDiscountService;
import com.apis.currencyexchange.service.CurrencyExchangeService;
import java.util.Arrays;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BillController {

  private static final Logger logger = LoggerFactory.getLogger(BillController.class);
  private final CurrencyExchangeService currencyExchangeService;
  private final BillDiscountService billDiscountService;

  public BillController(
      CurrencyExchangeService currencyExchangeService, BillDiscountService billDiscountService) {
    this.currencyExchangeService = currencyExchangeService;
    this.billDiscountService = billDiscountService;
  }

  @PostMapping("/calculate")
  public ResponseEntity<ItemBillResponse> calculate(@Valid @RequestBody ItemBillRequest request) {
    UserType userType;
    try {
      userType = UserType.valueOf(request.getUserType().toUpperCase());
    } catch (IllegalArgumentException e) {
      String validTypes = Arrays.toString(UserType.values());
      throw new IllegalArgumentException(
          "Invalid user type: " + request.getUserType() + ". Valid types are: " + validTypes);
    }

    double originalBill = request.getItems().stream().mapToDouble(Item::getPrice).sum();
    double discountedTotal =
        billDiscountService.applyDiscount(
            request.getItems(), userType, request.getCustomerTenure());
    double currencyExchangedTotal =
        currencyExchangeService.convert(
            discountedTotal, request.getOriginalCurrency(), request.getTargetCurrency());

    return ResponseEntity.ok(
        new ItemBillResponse(originalBill, discountedTotal, currencyExchangedTotal));
  }
}
