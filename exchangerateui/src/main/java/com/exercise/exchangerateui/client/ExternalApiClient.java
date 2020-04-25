package com.exercise.exchangerateui.client;

import client.exchangerate.DefaultApi;
import client.exchangerate.ExchangeRates;
import client.swagger.ApiException;
import exceptions.AuthenticationException;
import exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExternalApiClient {
  private DefaultApi defaultApi;

  public ExternalApiClient(final DefaultApi defaultApi) {
    this.defaultApi = defaultApi;
  }

  public ExchangeRates getExchangeRates(int months) throws AuthenticationException {
    log.info("Requesting external exchange rates for {} months", months);
    try {
      return defaultApi.exchangeratesGet(months);
    } catch (ApiException e) {
      if(e.getCode()== HttpStatus.UNAUTHORIZED.value()){
        log.error("ApiException retrieving exchange rates:{}", e.getResponseBody(), e);
        throw new AuthenticationException("Failed to parse client response");
      }
      else {
        log.error("ApiException retrieving exchange rates:{}", e.getResponseBody(), e);
        throw new ClientException("Failed to parse client response");
      }
    }
  }

}
