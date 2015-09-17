package com.kolejnik.bizdays.holiday;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class MovableYearlyHoliday extends YearlyHoliday {

    // TODO: parametrize them
    private final static int MIN_CACHED_YEAR = 1900;
    private final static int MAX_CACHED_YEAR = 2100;

    protected Map<Integer, LocalDate> cachedDates = new HashMap<Integer, LocalDate>();

    protected abstract LocalDate calculateByYear(int year);

    @Override
    public boolean isHoliday(LocalDate date) {
        int year = date.getYear();
        LocalDate holiday = getByYear(year);
        return holiday.isEqual(date);
    }

    @Override
    public LocalDate getByYear(int year) {
        LocalDate holiday = cachedDates.get(year);
        if (holiday == null) {
            holiday = calculateByYear(year);
            if (year >= MIN_CACHED_YEAR && year <= MAX_CACHED_YEAR) {
                cachedDates.put(year, holiday);
            }
        }
        return holiday;
    }

}
