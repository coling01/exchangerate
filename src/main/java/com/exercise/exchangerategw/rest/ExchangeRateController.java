package com.exercise.exchangerategw.rest;

import com.exercise.exchangerategw.service.ExchangeRateService;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import server.exchangerate.ExchangeRates;

@Slf4j
@Controller
public class ExchangeRateController {
  private ExchangeRateService exchangeRateService;

  public ExchangeRateController(final ExchangeRateService exchangeRateService) {
    this.exchangeRateService = exchangeRateService;
  }

  @GetMapping(value = "/exchangerates")
  public ResponseEntity<ExchangeRates> getExchangeRate() {
    log.info("Requested getExchangeRate");
    LocalDate targetDate = LocalDate.now();
    final String currencyCodes="GBP,HKD,USD";
    return ResponseEntity.ok(exchangeRateService.getExchangeRate(targetDate, currencyCodes));
  }

}
