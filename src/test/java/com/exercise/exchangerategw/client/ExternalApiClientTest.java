package com.exercise.exchangerategw.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

import client.exchangerate.ClientExchangeRates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class ExternalApiClientTest {

  private ExternalApiClient externalApiClient;

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(8800);

  @Before
  public void before() {
    ObjectMapper objectMapper = new ObjectMapper();
    externalApiClient = new ExternalApiClient(new RestTemplateBuilder().build(), "http://localhost:8800/stubbed", objectMapper);
  }

  @Test
  public void shouldMapJsonToGeneratedResponseObject() throws JsonProcessingException {
    stubApiClient("/stubbed/2020-01-10?symbols=GBP,HKD,USD", 200, clientResponseJson());
    ClientExchangeRates response = externalApiClient.getExchangeRates("2020-01-10", "GBP,HKD,USD");
    assertResponseBody(response);
  }

  @Test(expected = HttpClientErrorException.class)
  public void shouldSurfaceClientException() throws JsonProcessingException {
    stubApiClient("/stubbed/2020-01-10?symbols=GBP,HKD,USD", 400, clientResponseJson());
    externalApiClient.getExchangeRates("2020-01-10", "GBP,HKD,USD");
  }

  @Test(expected = HttpServerErrorException.class)
  public void shouldSurfaceServerException() throws JsonProcessingException {
    stubApiClient("/stubbed/2020-01-10?symbols=GBP,HKD,USD", 500, clientResponseJson());
    externalApiClient.getExchangeRates("2020-01-10", "GBP,HKD,USD");
  }

  private void stubApiClient(final String stubbedUrl, final int responseCode, final String jsonResponse) {
    wireMockRule.stubFor(WireMock.get(urlEqualTo(stubbedUrl))
      .willReturn(aResponse()
        .withHeader("Content-Type", "text/json; charset=utf-8")
        .withBody(clientResponseJson())
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
