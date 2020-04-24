package com.exercise.exchangerategw.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static java.util.Arrays.asList;
import com.exercise.exchangerategw.client.ExternalApiClient;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateServiceTest {

  @Mock
  DatesService datesService;
  @Mock
  private ExternalApiClient externalApiClient;
  @Mock
  private MapperService mapperService;

  private ExchangeRateService exchangeRateService;

  @Before
  public void before(){
    exchangeRateService = new ExchangeRateService(datesService, externalApiClient, mapperService);
  }

  @Test
  public void shouldCallUnderlyingServices() {
    when(datesService.calcTargetDates(any(LocalDate.class), anyInt())).thenReturn(asList(LocalDate.now()));
    exchangeRateService.getExchangeRate(LocalDate.of(2020,1,31), "GBD,HKD,USD", 1);
    verify(datesService).calcTargetDates(any(LocalDate.class), anyInt());
    verify(externalApiClient).getExchangeRates(anyString(), anyString());
    verify(mapperService).mapClientExchangeRates(anyList());
  }

}
