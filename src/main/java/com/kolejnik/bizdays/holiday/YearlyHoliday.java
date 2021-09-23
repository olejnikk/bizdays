package com.kolejnik.bizdays.holiday;

import java.time.LocalDate;

public abstract class YearlyHoliday implements Holiday {

    public abstract LocalDate getByYear(int year);

    @Override
    public LocalDate nextAfter(LocalDate date) {
        int year = date.getYear();
        LocalDate holiday = getByYear(year);
        if (holiday.isAfter(date)) {
            return holiday;
        }
        return getByYear(year + 1);
    }

}
