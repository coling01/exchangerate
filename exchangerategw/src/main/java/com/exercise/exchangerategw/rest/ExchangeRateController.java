package com.exercise.exchangerategw.rest;

import com.exercise.exchangerategw.service.ExchangeRateService;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import server.exchangerate.ExchangeRates;

@Slf4j
@Controller
public class ExchangeRateController {
  private static final String CURRENCY_CODES = "GBP,HKD,USD";
  private static final int NUMBER_OF_MONTHS = 1;
  private ExchangeRateService exchangeRateService;

  public ExchangeRateController(final ExchangeRateService exchangeRateService) {
    this.exchangeRateService = exchangeRateService;
  }

  @GetMapping(value = "/exchangerates")
  public ResponseEntity<ExchangeRates> getExchangeRate(@RequestParam(value = "months", required = false, defaultValue = "1") Integer months) {
    log.info("Incoming request to getExchangeRate");
    LocalDate targetDate = LocalDate.now();
    return ResponseEntity.ok(exchangeRateService.getExchangeRate(targetDate, CURRENCY_CODES, months));
  }

}
