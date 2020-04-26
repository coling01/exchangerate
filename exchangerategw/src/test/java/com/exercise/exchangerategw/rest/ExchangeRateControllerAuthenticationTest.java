package com.exercise.exchangerategw.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.exercise.exchangerategw.service.ExchangeRateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = {
  "application.authentication.enabled=true",
  "application.authentication.user=testuser",
  "application.authentication.password=testpassword",
})
@AutoConfigureMockMvc
public class ExchangeRateControllerAuthenticationTest {

  @MockBean
  ExchangeRateService exchangeRateService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldGetUnauthorisedForNoCredentials() throws Exception {
    mockMvc.perform(get("/exchangerates"))
      .andExpect(status().isUnauthorized());
  }

  @Test
  public void shouldGetUnauthorisedForBadCredentials() throws Exception {
    mockMvc.perform(get("/exchangerates")
      .header(HttpHeaders.AUTHORIZATION,
        "Basic " + Base64Utils.encodeToString("admin:bad".getBytes())))
      .andExpect(status().isUnauthorized());
  }

  @Test
  public void shouldGetOkForBadCredentials() throws Exception {
    mockMvc.perform(get("/exchangerates")
      .header(HttpHeaders.AUTHORIZATION,
        "Basic " + Base64Utils.encodeToString("testuser:testpassword".getBytes())))
      .andExpect(status().isOk());
  }
}
