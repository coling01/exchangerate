package com.exercise.exchangerategw.client;

import client.exchangerate.ClientExchangeRates;
import com.exercise.exchangerategw.exceptions.MappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ExternalApiClient {
  private RestTemplate restTemplate;
  private String baseUrl;
  private ObjectMapper objectMapper;

  public ExternalApiClient(final RestTemplate restTemplate, final String baseUrl, final ObjectMapper objectMapper) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
    this.objectMapper = objectMapper;
  }

  public ClientExchangeRates getExchangeRates(String date) {
    final String url = baseUrl + "/" + date;
    log.info("Requesting external exchange rates from:{}", url);
    try {
      ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
      return objectMapper.readValue(response.getBody(), ClientExchangeRates.class);
    } catch (JsonProcessingException e) {
      log.error("Failed to parse client response to ClientExchangeRates.class", e);
      throw new MappingException("Failed to parse client response");
    }
  }

}
