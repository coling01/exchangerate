package com.exercise.exchangerategw.service;

import client.exchangerate.ClientExchangeRates;
import com.exercise.exchangerategw.exceptions.ClientException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import server.exchangerate.CurrencyExchangeRate;
import server.exchangerate.ExchangeRate;
import server.exchangerate.ExchangeRates;

@Slf4j
@Service
public class MapperService {

  ExchangeRates mapClientExchangeRates(List<ClientExchangeRates> inboundRates) {
    ExchangeRates outbound = new ExchangeRates();
    for (ClientExchangeRates inbound : inboundRates) {
      mapBaseCurrency(outbound, inbound.getBase());
    }
    mapRates(outbound, inboundRates);
    return outbound;
  }

  private void mapBaseCurrency(ExchangeRates outbound, String inboundBase) {
    if (outbound.getBase() != null && !outbound.getBase().equals(inboundBase)) {
      log.error("Mapping error. ExchangeRate base:{} differs from base:{}", outbound.getBase(), inboundBase);
      throw new ClientException("None matching base rates from client");
    }
    outbound.setBase(inboundBase);
  }

  private void mapRates(ExchangeRates outbound, List<ClientExchangeRates> inboundRates) {
    Map<String, List<ExchangeRate>> ratesByCurrency = new HashMap<>();
    for (ClientExchangeRates inbound : inboundRates) {
      // The client response returns a map of <String,Double>
      Map<String, Double> rates = inbound.getRates();
      for (Map.Entry entry : rates.entrySet()) {
        addRateToList(ratesByCurrency, (String) entry.getKey(), inbound.getDate(), ((Double) entry.getValue()).floatValue());
      }
    }
    List<CurrencyExchangeRate> currencyList = new ArrayList<>();
    for (String currency : ratesByCurrency.keySet()) {
      CurrencyExchangeRate rate = new CurrencyExchangeRate();
      rate.setCurrencyCode(currency);
      rate.setRates(ratesByCurrency.get(currency));
      currencyList.add(rate);
    }
    outbound.setCurrencies(currencyList);
  }

  private void addRateToList(Map<String, List<ExchangeRate>> ratesByCurrency, final String currency, final String date, final Float rate) {
    ExchangeRate exchangeRate = new ExchangeRate();
    exchangeRate.setDate(LocalDate.parse(date));
    exchangeRate.setRate(rate);
    if (ratesByCurrency.containsKey(currency)) {
      List<ExchangeRate> dateList = ratesByCurrency.get(currency);
      dateList.add(exchangeRate);
      ratesByCurrency.put(currency, dateList);
    } else {
      List<ExchangeRate> dateList = new ArrayList<>();
      dateList.add(exchangeRate);
      ratesByCurrency.put(currency, dateList);
    }
  }

}
