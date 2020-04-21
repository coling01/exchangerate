package com.exercise.exchangerategw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {

  @Value("${application.apiclient.address}")
  private String apiClientUrl;

  @Bean
  public String apiClientUrl(){
    return apiClientUrl;
  }

  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplateBuilder()
      .build();
  }

}
