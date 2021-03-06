package com.exercise.exchangerategw.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

import client.exchangerate.ClientExchangeRates;
import client.exchangerate.DefaultApi;
import client.swagger.ApiClient;
import com.exercise.exchangerategw.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class ExternalApiClientTest {

  private ExternalApiClient externalApiClient;

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(8800);

  @Before
  public void before() {
    DefaultApi defaultApi = new DefaultApi();
    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath("http://localhost:8800/stubbed");
    defaultApi.setApiClient(apiClient);
    externalApiClient = new ExternalApiClient(defaultApi);
  }

  @Test
  public void shouldMapJsonToGeneratedResponseObject() throws JsonProcessingException {
    stubApiClient("/stubbed/api/2020-01-10?symbols=GBP", 200, clientResponseJson());
    ClientExchangeRates response = externalApiClient.getExchangeRates("2020-01-10", "GBP");
    assertResponseBody(response);
  }

  @Test(expected = ClientException.class)
  public void shouldSurfaceClientException() throws JsonProcessingException {
    stubApiClient("/stubbed/api/2020-01-10?symbols=GBP", 400, clientResponseJson());
    externalApiClient.getExchangeRates("2020-01-10", "GBP");
  }

  @Test(expected = ClientException.class)
  public void shouldSurfaceServerException() throws JsonProcessingException {
    stubApiClient("/stubbed/api/2020-01-10?symbols=GBP", 500, clientResponseJson());
    externalApiClient.getExchangeRates("2020-01-10", "GBP");
  }

  private void stubApiClient(final String stubbedUrl, final int responseCode, final String jsonResponse) {
    wireMockRule.stubFor(WireMock.get(urlEqualTo(stubbedUrl))
      .willReturn(aResponse()
        .withHeader("Content-Type", "application/json")
        .withBody(jsonResponse)
        .withStatus(responseCode)
      ));
  }

  private String clientResponseJson() {
    return "{\"base\":\"EUR\",\"rates\":{\"GBP\":0.85215,\"HKD\":8.6978,\"USD\":1.1194},\"date\":\"2020-01-06\"}";
  }

  private void assertResponseBody(ClientExchangeRates exchangeRates) {
    assertEquals("EUR", exchangeRates.getBase());
    assertEquals(3, exchangeRates.getRates().size());
    assertEquals("0.85215", exchangeRates.getRates().get("GBP").toString());
    assertEquals("8.6978", exchangeRates.getRates().get("HKD").toString());
    assertEquals("1.1194", exchangeRates.getRates().get("USD").toString());
    assertEquals("2020-01-06", exchangeRates.getDate());
  }
}
