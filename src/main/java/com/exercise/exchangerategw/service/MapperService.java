package com.exercise.exchangerategw.service;

import client.exchangerate.ClientExchangeRates;
import com.exercise.exchangerategw.exceptions.MappingException;
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
    if(outbound.getBase()!=null && outbound.getBase()!=inboundBase){
      log.error("Mapping error. ExchangeRate base:{} differs from base:{}", outbound.getBase(), inboundBase);
      throw new MappingException("None matching base rates from client");
    }
    outbound.setBase(inboundBase);
  }

  private void mapRates(ExchangeRates outbound, List<ClientExchangeRates> inboundRates) {
    Map<String,List<ExchangeRate>> ratesByCurrency=new HashMap<>();
    for (ClientExchangeRates inbound : inboundRates) {
      Map<String,String> rates = inbound.getRates();
      for( String currency: rates.keySet()){
        addRateToList(ratesByCurrency, currency, inbound.getDate(), rates.get(currency));
      }
    }
    List<CurrencyExchangeRate> currencyList = new ArrayList<>();
    for(String currency: ratesByCurrency.keySet()){
      CurrencyExchangeRate rate = new CurrencyExchangeRate();
      rate.setCurrencyCode(currency);
      rate.setRates(ratesByCurrency.get(currency));
      currencyList.add(rate);
    }
    outbound.setCurrencies(currencyList);
  }

  private void addRateToList(Map<String,List<ExchangeRate>> ratesByCurrency, final String currency, final String date, final String rate){
    ExchangeRate exchangeRate = new ExchangeRate();
    exchangeRate.setDate(date);
    exchangeRate.setRate(Float.valueOf(rate));
    if(ratesByCurrency.containsKey(currency)){
      List<ExchangeRate> dateList = ratesByCurrency.get(currency);
      dateList.add(exchangeRate);
      ratesByCurrency.put(currency, dateList);
    }
    else {
      List<ExchangeRate> dateList=new ArrayList<>();
      dateList.add(exchangeRate);
      ratesByCurrency.put(currency, dateList);
    }
  }

}
