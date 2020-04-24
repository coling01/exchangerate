package com.exercise.exchangerategw.client;

import static org.junit.Assert.assertEquals;

import client.exchangerate.ClientExchangeRates;
import client.exchangerate.DefaultApi;
import client.swagger.ApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class FunctionalApiClientTest {

  private ExternalApiClient externalApiClient;

  @Before
  public void before() {
    DefaultApi defaultApi = new DefaultApi();
    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath("https://api.ratesapi.io");
    defaultApi.setApiClient(apiClient);
    externalApiClient = new ExternalApiClient(defaultApi);
  }

  @Test
  public void shouldMapJsonToGeneratedResponseObject() throws JsonProcessingException {
    ClientExchangeRates response = externalApiClient.getExchangeRates("2020-01-10", "GBP");
    assertResponseBody(response);
  }

  private void assertResponseBody(ClientExchangeRates exchangeRates) {
    assertEquals("EUR", exchangeRates.getBase());
    assertEquals(1, exchangeRates.getRates().size());
    assertEquals("0.8481", exchangeRates.getRates().get("GBP").toString());
    assertEquals("2020-01-10", exchangeRates.getDate());
  }

}
