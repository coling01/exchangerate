package com.exercise.exchangerategw.rest;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import server.exchangerate.ExchangeRates;

public class SpecTest {

  @Ignore
  @Test
  public void shouldMapServerResponseToExchangeRatesObject() {
    ExchangeRates exchangeRates = null;
    try {
      exchangeRates = new ObjectMapper().readValue(clientResponseJson(), ExchangeRates.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    assertEquals("EUR", exchangeRates.getBase());
//    assertEquals("0.85215", exchangeRates.getRates().get("GBP").toString());
//    assertEquals("8.6978", exchangeRates.getRates().get("HKD").toString());
//    assertEquals("1.1194", exchangeRates.getRates().get("USD").toString());
//    assertEquals("2020-01-06", exchangeRates.getDate());
  }

  private String clientResponseJson(){
    return "{\"base\":\"EUR\",\"currencies\":[{\"currency_code\":\"HKD\",\"rates\":[{\"date\":\"2020-04-22\",\"rate\":8.422}]},{\"currency_code\":\"GBP\",\"rates\":[{\"date\":\"2020-04-22\",\"rate\":0.8792}]},{\"currency_code\":\"USD\",\"rates\":[{\"date\":\"2020-04-22\",\"rate\":1.0867}]}]}";
  }

}
