package com.exercise.exchangerategw.integration;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import client.exchangerate.ClientExchangeRates;
import com.exercise.exchangerategw.client.ExternalApiClient;
import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeRateIntegrationTest {
  @MockBean
  private ExternalApiClient externalApiClient;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnExchangeRatesForMockedClient() throws Exception {
    when(externalApiClient.getExchangeRates(anyString(), anyString())).thenReturn(dummyExchangeRates());
    mockMvc.perform(MockMvcRequestBuilders.get("/exchangerates?months=1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.base").value("EUR"))
      .andExpect(jsonPath("$.currencies.length()").value(1))
      .andExpect(jsonPath("$.currencies.[0].currency_code").value("GBP"))
      .andExpect(jsonPath("$.currencies.[0].rates.length()").value(1))
      .andExpect(jsonPath("$.currencies.[0].rates.[0].date").value("2020-02-28"))
      .andExpect(jsonPath("$.currencies.[0].rates.[0].rate").value(0.801));
  }

  @Test
  public void shouldSurfaceApiError() throws Exception {
    when(externalApiClient.getExchangeRates(anyString(), anyString())).thenThrow(new RuntimeException("Any exception"));
    mockMvc.perform(MockMvcRequestBuilders.get("/exchangerates"))
      .andExpect(status().is5xxServerError());
  }

  private ClientExchangeRates dummyExchangeRates() {
    ClientExchangeRates clientExchangeRates = new ClientExchangeRates();
    clientExchangeRates.setBase("EUR");
    Map<String, Double> rates = Collections.singletonMap("GBP", 0.801);
    clientExchangeRates.setRates(rates);
    clientExchangeRates.setDate("2020-02-28");
    return clientExchangeRates;
  }

}
