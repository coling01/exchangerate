package com.exercise.exchangerategw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class DatesService {

  /*
  We need to calculate valid dates going back 1 month at a time for 6 months
  i.e. 31-05 goes back to 30-04
   */
  public List<LocalDate> calcTargetDates(LocalDate startDate) {
    return IntStream.range(0, 1).boxed().map(n -> startDate.minusMonths(n)).collect(Collectors.toList());
  }

}
