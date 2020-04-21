package com.exercise.exchangerategw.client;

import client.exchangerate.ExchangeRates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Resource
public class ExternalApiClient {
  private RestTemplate restTemplate;
  private String baseUrl;
  private ObjectMapper objectMapper;

  public ExternalApiClient(final RestTemplate restTemplate, final String baseUrl, final ObjectMapper objectMapper) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
    this.objectMapper = objectMapper;
  }

  public ResponseEntity<ExchangeRates> getExchangeRates(String date) throws JsonProcessingException {
    final String url = baseUrl + "/" + date;
    log.info("Requesting external exchange rates from:{}", url);
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    return ResponseEntity.ok(objectMapper.readValue(response.getBody(), ExchangeRates.class));
  }

}
