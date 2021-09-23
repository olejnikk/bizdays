package com.kolejnik.bizdays;

import java.time.LocalDate;

public interface BusinessDayCalculator {

    boolean isBusinessDay(LocalDate date);

    boolean isHoliday(LocalDate date);

    LocalDate nextBusinessDay();

    LocalDate businessDayAfter(LocalDate date);

    LocalDate businessDayBefore(LocalDate date);

    int businessDaysBetween(LocalDate from, LocalDate to);

    LocalDate plus(LocalDate date, int businessDaysCount);

    LocalDate minus(LocalDate date, int businessDaysCount);

}
