package com.exercise.exchangerategw.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import server.exchangerate.ExchangeRates;

@Service
public class ExchangeRateService {

  public ExchangeRates getExchangeRate(){
    String json = tempJson();
    try {
      ExchangeRates e =new ObjectMapper().readValue(json, ExchangeRates.class);
      return e;
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new RuntimeException("Gone wrong");
    }
  }

  private String tempJson(){
    return "{\n" +
      "  \"base\": \"EUR\",\n" +
      "  \"currencies\": [\n" +
      "    {\n" +
      "      \"currency_code\": \"GBP\",\n" +
      "      \"rates\": [\n" +
      "        {\n" +
      "          \"date\": \"2020-04-20\",\n" +
      "          \"rate\": 0.802\n" +
      "        }\n" +
      "      ]\n" +
      "    }\n" +
      "  ]\n" +
      "}";
  }

}
