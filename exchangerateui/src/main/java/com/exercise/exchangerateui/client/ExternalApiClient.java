package com.exercise.exchangerateui.client;

import client.exchangerate.DefaultApi;
import client.exchangerate.ExchangeRates;
import client.swagger.ApiException;
import exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExternalApiClient {
  private DefaultApi defaultApi;

  public ExternalApiClient(final DefaultApi defaultApi) {
    this.defaultApi = defaultApi;
  }

  public ExchangeRates getExchangeRates(String date, int months, String currencyCodes) {
    log.info("Requesting external exchange rates for date:{}", date);
    try {
      return defaultApi.exchangeratesGet(months);
    } catch (ApiException e) {
      log.error("Failed to parse client response to ClientExchangeRates.class", e);
      throw new ClientException("Failed to parse client response");
    }
  }

}
