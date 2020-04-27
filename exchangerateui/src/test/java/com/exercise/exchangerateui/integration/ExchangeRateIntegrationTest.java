package com.exercise.exchangerateui.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import client.exchangerate.CurrencyExchangeRate;
import client.exchangerate.ExchangeRate;
import client.exchangerate.ExchangeRates;
import com.exercise.exchangerateui.client.ExternalApiClient;
import java.util.Collections;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.threeten.bp.LocalDate;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = {
  "application.authentication.enabled=false"
})
@AutoConfigureMockMvc
public class ExchangeRateIntegrationTest {
  @MockBean
  private ExternalApiClient externalApiClient;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnExchangeRatesForMockedClient() throws Exception {
    when(externalApiClient.getExchangeRates(anyInt())).thenReturn(dummyExchangeRates());
    mockMvc.perform(MockMvcRequestBuilders.get("/exchangerates?months=1"))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("2020-02-28")))
      .andExpect(content().string(containsString("GBP")))
      .andExpect(content().string(containsString("0.805")))
      .andDo(MockMvcResultHandlers.print());
  }

  private ExchangeRates dummyExchangeRates() {
    ExchangeRates exchangeRates = new ExchangeRates();
    exchangeRates.setBase("EUR");
    CurrencyExchangeRate currencyExchangeRate = new CurrencyExchangeRate();
    currencyExchangeRate.setCurrencyCode("GBP");
    ExchangeRate exchangeRate = new ExchangeRate();
    exchangeRate.setRate(0.805f);
    exchangeRate.setDate(LocalDate.parse("2020-02-28"));
    currencyExchangeRate.setRates(Collections.singletonList(exchangeRate));
    exchangeRates.setCurrencies(Collections.singletonList(currencyExchangeRate));
    return exchangeRates;
  }

}
