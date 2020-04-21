package com.exercise.exchangerategw.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ExchangeRateController {

  @GetMapping(value = "/exchangerates")
  public ResponseEntity<String> getExchangeRate(){
    log.info("Requested getExchangeRate");
    return ResponseEntity.ok("exchange rates following");
  }

}
