package com.exercise.exchangerategw.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import client.exchangerate.ExchangeRates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class SpecTest {

  @Test
  public void shouldMapClientApiResponseToExchangeRatesObject() throws JsonProcessingException {
    ExchangeRates exchangeRates = new ObjectMapper().readValue(clientResponseJson(), ExchangeRates.class);
    assertEquals("EUR", exchangeRates.getBase());
    assertEquals(3, exchangeRates.getRates().size());
    assertEquals("0.85215", exchangeRates.getRates().get("GBP").toString());
    assertEquals("8.6978", exchangeRates.getRates().get("HKD").toString());
    assertEquals("1.1194", exchangeRates.getRates().get("USD").toString());
    assertEquals("2020-01-06", exchangeRates.getDate());
  }

  private String clientResponseJson(){
    return "{\"base\":\"EUR\",\"rates\":{\"GBP\":0.85215,\"HKD\":8.6978,\"USD\":1.1194},\"date\":\"2020-01-06\"}";
  }

}
