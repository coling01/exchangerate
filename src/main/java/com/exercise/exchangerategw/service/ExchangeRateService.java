package com.exercise.exchangerategw.service;

import client.exchangerate.ClientExchangeRates;
import com.exercise.exchangerategw.client.ExternalApiClient;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import server.exchangerate.ExchangeRates;

@Service
public class ExchangeRateService {
  private DatesService datesService;
  private ExternalApiClient externalApiClient;
  private MapperService mapperService;

  public ExchangeRateService(final DatesService datesService, final ExternalApiClient externalApiClient, final MapperService mapperService) {
    this.datesService = datesService;
    this.externalApiClient = externalApiClient;
    this.mapperService = mapperService;
  }

  public ExchangeRates getExchangeRate(LocalDate startDate) {
    List<ClientExchangeRates> responses = datesService.calcTargetDates(startDate).stream()
      .map(date -> externalApiClient.getExchangeRates(date.toString())).collect(Collectors.toList());
    return mapperService.mapClientExchangeRates(responses);
  }

}
