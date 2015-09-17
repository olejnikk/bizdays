package com.kolejnik.bizdays;

import java.time.LocalDate;

public interface BusinessDayCalculator {

    public boolean isBusinessDay(LocalDate date);

    public boolean isHoliday(LocalDate date);

    public LocalDate nextBusinessDay();

    public LocalDate businessDayAfter(LocalDate date);

    public LocalDate businessDayBefore(LocalDate date);

    public int businessDaysBetween(LocalDate from, LocalDate to);

    public LocalDate plus(LocalDate date, int businessDaysCount);

    public LocalDate minus(LocalDate date, int businessDaysCount);
}
