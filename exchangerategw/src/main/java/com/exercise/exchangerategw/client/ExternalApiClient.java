package com.exercise.exchangerategw.client;

import client.exchangerate.ClientExchangeRates;
import client.exchangerate.DefaultApi;
import client.swagger.ApiException;
import com.exercise.exchangerategw.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDate;

@Slf4j
@Service
public class ExternalApiClient {
  private DefaultApi defaultApi;

  public ExternalApiClient(final DefaultApi defaultApi) {
    this.defaultApi = defaultApi;
  }

  public ClientExchangeRates getExchangeRates(String date, String currencyCodes) {
    log.info("Requesting external exchange rates for date:{}", date);
    try {
      return defaultApi.apiDateGet(LocalDate.parse(date), currencyCodes);
    } catch (ApiException e) {
      log.error("Failed to parse client response to ClientExchangeRates.class", e);
      throw new ClientException("Failed to parse client response");
    }
  }

}
