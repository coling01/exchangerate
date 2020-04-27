package com.exercise.exchangerateui.client;

import static org.junit.Assert.assertEquals;

import client.exchangerate.DefaultApi;
import client.exchangerate.ExchangeRates;
import client.swagger.ApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Base64;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore("Hits actual web endpoint. Do not run in pipeline")
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class FunctionalApiClientTest {

  private ExternalApiClient externalApiClient;

  @Before
  public void before() {
    DefaultApi defaultApi = new DefaultApi();
    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath("http://localhost:8080");
    String notEncoded = "gwadmin" + ":" + "gwadmin";
    String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
    apiClient.addDefaultHeader("Authorization", "Basic " + encodedAuth);
    defaultApi.setApiClient(apiClient);
    externalApiClient = new ExternalApiClient(defaultApi);
  }

  @Test
  public void shouldCallAuthenticatedApiClient() throws JsonProcessingException {
    ExchangeRates response = externalApiClient.getExchangeRates(3);
    assertResponseBody(response);
  }

  private void assertResponseBody(ExchangeRates exchangeRates) {
    assertEquals("EUR", exchangeRates.getBase());
    assertEquals(3, exchangeRates.getCurrencies().size());
    assertEquals("HKD", exchangeRates.getCurrencies().get(0).getCurrencyCode());
    assertEquals("GBP", exchangeRates.getCurrencies().get(1).getCurrencyCode());
    assertEquals("USD", exchangeRates.getCurrencies().get(2).getCurrencyCode());
  }

}
