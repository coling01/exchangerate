package com.exercise.exchangerategw.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExchangeRateController {

  @GetMapping(value = "/exchangerates")
  public ResponseEntity<String> getExchangeRate(){
    return ResponseEntity.ok("exchange rates following");
  }

}
