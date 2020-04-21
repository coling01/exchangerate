package com.exercise.exchangerategw.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.exchangerate.ExchangeRates;

class ExchangeRateServiceTest {

  private ExchangeRateService exchangeRateService;

  @BeforeEach
  public void before(){
    exchangeRateService = new ExchangeRateService();
  }

  @Test
  public void shouldReturnPopulatedExchangeRateObject(){
    ExchangeRates exchangeRate = exchangeRateService.getExchangeRate();
    assertEquals("EUR", exchangeRate.getBase());
    assertEquals(1, exchangeRate.getCurrencies().size());
    assertEquals("GBP", exchangeRate.getCurrencies().get(0).getCurrencyCode());
    assertEquals(1, exchangeRate.getCurrencies().get(0).getRates().size());
    assertEquals("2020-04-20", exchangeRate.getCurrencies().get(0).getRates().get(0).getDate());
    assertEquals(0.802f, exchangeRate.getCurrencies().get(0).getRates().get(0).getRate());
  }

}
