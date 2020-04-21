package com.exercise.exchangerategw.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ExchangeRateControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldGetOkFoGoodUrl() throws Exception {
    mockMvc.perform(get("/exchangerates"))
      .andExpect(status().isOk());
  }

  @Test
  public void shouldGet404ResponseFromBadUrl() throws Exception {
    mockMvc.perform(get("/badurl"))
      .andExpect(status().isNotFound());
  }
}
