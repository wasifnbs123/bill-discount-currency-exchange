package com.apis.currencyexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BillExchangeApplication {

  public static void main(String[] args) {
    SpringApplication.run(BillExchangeApplication.class, args);
  }
}
