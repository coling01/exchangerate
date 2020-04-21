package com.exercise.exchangerategw.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import client.exchangerate.ExchangeRates;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public void before(){
    ObjectMapper objectMapper = new ObjectMapper();
    externalApiClient = new ExternalApiClient(new RestTemplateBuilder().build(), "http://localhost:8800/stubbed", objectMapper);
  }

  @Test
  public void shouldMapJsonToGeneratedResponseObject() throws JsonProcessingException {
    stubApiClient("/stubbed/2020-01-10", 200, clientResponseJson());
    ResponseEntity<ExchangeRates> response = externalApiClient.getExchangeRates("2020-01-10");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertResponseBody(response.getBody());
  }

  @Test(expected = HttpClientErrorException.class)
  public void shouldSurfaceClientException() throws JsonProcessingException {
    stubApiClient("/stubbed/2020-01-10", 400, clientResponseJson());
    externalApiClient.getExchangeRates("2020-01-10");
  }

  @Test(expected = HttpServerErrorException.class)
  public void shouldSurfaceServerException() throws JsonProcessingException {
    stubApiClient("/stubbed/2020-01-10", 500, clientResponseJson());
    externalApiClient.getExchangeRates("2020-01-10");
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

  private void assertResponseBody(ExchangeRates exchangeRates){
    assertEquals("EUR", exchangeRates.getBase());
    assertEquals(3, exchangeRates.getRates().size());
    assertEquals("0.85215", exchangeRates.getRates().get("GBP").toString());
    assertEquals("8.6978", exchangeRates.getRates().get("HKD").toString());
    assertEquals("1.1194", exchangeRates.getRates().get("USD").toString());
    assertEquals("2020-01-06", exchangeRates.getDate());
  }
}
