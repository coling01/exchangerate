package com.exercise.exchangerateui.rest;

import client.exchangerate.CurrencyExchangeRate;
import client.exchangerate.ExchangeRate;
import client.exchangerate.ExchangeRates;
import com.exercise.exchangerateui.client.ExternalApiClient;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.threeten.bp.LocalDate;

@Slf4j
@Controller
public class UiController {

  private ExternalApiClient apiClient;

  public UiController(final ExternalApiClient apiClient) {
    this.apiClient = apiClient;
  }

  @GetMapping(value = "/exchangerates")
  public String exchangeRates(
    @RequestParam(name = "months", required = false, defaultValue = "1")
      int months, Model model) {
    log.info("GET Retrieving {} months exchange rates", months);
    ExchangeRates rates = apiClient.getExchangeRates(months);
    List<CurrencyExchangeRate> ratesCurrencies = rates.getCurrencies();
    model.addAttribute("months", months);
    model.addAttribute("dates", getCurrencyDates(ratesCurrencies));
    model.addAttribute("rates", ratesCurrencies);

    return "exchangerates";
  }

  private List<LocalDate> getCurrencyDates(List<CurrencyExchangeRate> ratesCurrencies) {
    List<LocalDate> dates = new ArrayList();
    if (ratesCurrencies.size() > 0) {
      CurrencyExchangeRate currencyExchangeRate = ratesCurrencies.get(0);
      for (ExchangeRate exchangeRate : currencyExchangeRate.getRates()) {
        dates.add(exchangeRate.getDate());
      }
    }
    return dates;
  }
}
