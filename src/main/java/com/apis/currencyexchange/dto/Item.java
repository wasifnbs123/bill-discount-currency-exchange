package com.apis.currencyexchange.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
  @NotBlank private String name;
  @NotNull private String category;
  @Positive private double price;
}
