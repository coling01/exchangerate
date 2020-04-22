package com.exercise.exchangerategw.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static java.util.Arrays.asList;
import client.exchangerate.ClientExchangeRates;
import com.exercise.exchangerategw.exceptions.MappingException;
import java.util.Collections;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import server.exchangerate.ExchangeRates;

public class MapperServiceTest {

  private MapperService mapperService;

  @Before
  public void before() {
    mapperService = new MapperService();
  }

  @Test(expected = MappingException.class)
  public void shouldThrowExceptionWhenDifferentBases() {
    ClientExchangeRates rates1 = new ClientExchangeRates();
    rates1.setBase("EUR");
    ClientExchangeRates rates2 = new ClientExchangeRates();
    rates2.setBase("GBP");
    mapperService.mapClientExchangeRates(asList(rates1, rates2));
  }

  @Test
  public void shouldMapSingleClientResponseToServerResponse() {
    ExchangeRates exchangeRates = mapperService.mapClientExchangeRates(asList(mockExchangeRates1(), mockExchangeRates2()));
    assertEquals("EUR", exchangeRates.getBase());
    assertEquals(1, exchangeRates.getCurrencies().size());
    assertEquals("GBP", exchangeRates.getCurrencies().get(0).getCurrencyCode());
    assertEquals(2, exchangeRates.getCurrencies().get(0).getRates().size());
    assertEquals("2020-02-28", exchangeRates.getCurrencies().get(0).getRates().get(0).getDate());
    assertEquals(0.801f, exchangeRates.getCurrencies().get(0).getRates().get(0).getRate());
    assertEquals("2020-01-28", exchangeRates.getCurrencies().get(0).getRates().get(1).getDate());
    assertEquals(0.799f, exchangeRates.getCurrencies().get(0).getRates().get(1).getRate());
  }

  private ClientExchangeRates mockExchangeRates1() {
    ClientExchangeRates clientExchangeRates = new ClientExchangeRates();
    clientExchangeRates.setBase("EUR");
    Map<String, Double> rates = Collections.singletonMap("GBP", 0.801);
    clientExchangeRates.setRates(rates);
    clientExchangeRates.setDate("2020-02-28");
    return clientExchangeRates;
  }

  private ClientExchangeRates mockExchangeRates2() {
    ClientExchangeRates clientExchangeRates = new ClientExchangeRates();
    clientExchangeRates.setBase("EUR");
    Map<String, Double> rates = Collections.singletonMap("GBP", 0.799);
    clientExchangeRates.setRates(rates);
    clientExchangeRates.setDate("2020-01-28");
    return clientExchangeRates;
  }
}
