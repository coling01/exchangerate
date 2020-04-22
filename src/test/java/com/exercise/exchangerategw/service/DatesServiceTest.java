package com.exercise.exchangerategw.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DatesServiceTest {
  DatesService datesService;

  @Before
  public void before(){
    datesService =  new DatesService();
  }

  @Test
  public void shouldCalculateLast6MonthsDates(){
    List<LocalDate> dateList = datesService.calcTargetDates(LocalDate.of(2020, 7, 31), 6);
    assertEquals(6, dateList.size());
    assertEquals("2020-07-31", dateList.get(0).toString());
    assertEquals("2020-06-30", dateList.get(1).toString());
    assertEquals("2020-05-31", dateList.get(2).toString());
    assertEquals("2020-04-30", dateList.get(3).toString());
    assertEquals("2020-03-31", dateList.get(4).toString());
    assertEquals("2020-02-29", dateList.get(5).toString());
  }
}
