package com.exercise.exchangerateui.config;

import client.exchangerate.DefaultApi;
import client.swagger.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

  @Value("${application.apiclient.address}")
  private String apiClientUrl;

  @Bean
  public String apiClientUrl(){
    return apiClientUrl;
  }

  @Bean
  public DefaultApi defaultApi(){
    DefaultApi defaultApi = new DefaultApi();
    ApiClient apiClient= new ApiClient();
    apiClient.setBasePath(apiClientUrl);
    defaultApi.setApiClient(apiClient);
    return defaultApi;
  }

}
