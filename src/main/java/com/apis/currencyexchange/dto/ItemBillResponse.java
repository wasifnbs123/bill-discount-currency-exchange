package com.apis.currencyexchange.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemBillResponse {
  private double originalBill;
  private double discountedAmount;
  private double netPayableAmountInTargetCurrency;
}
