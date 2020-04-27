package com.exercise.exchangerateui.config;

import client.exchangerate.DefaultApi;
import client.swagger.ApiClient;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

  @Value("${application.apiclient.address}")
  private String apiClientUrl;

  @Value("${application.apiclient.user}")
  private String apiClientUser;

  @Value("${application.apiclient.password}")
  private String apiClientPassword;

  @Bean
  public String apiClientUrl() {
    return apiClientUrl;
  }

  @Bean
  public DefaultApi defaultApi() {
    DefaultApi defaultApi = new DefaultApi();
    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath(apiClientUrl);
    apiClient.addDefaultHeader("Authorization", "Basic " + encodedAuth());
    defaultApi.setApiClient(apiClient);
    return defaultApi;
  }

  private String encodedAuth(){
    String auth = apiClientUser + ":" + apiClientPassword;
    return Base64.getEncoder().encodeToString(auth.getBytes());
  }

}
