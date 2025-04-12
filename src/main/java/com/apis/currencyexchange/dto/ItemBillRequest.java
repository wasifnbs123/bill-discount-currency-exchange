package com.apis.currencyexchange.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemBillRequest {
  @NotEmpty private List<@Valid Item> items;
  @NotBlank private String userType;

  @Min(0)
  private int customerTenure;

  @NotBlank private String originalCurrency;
  @NotBlank private String targetCurrency;
}
