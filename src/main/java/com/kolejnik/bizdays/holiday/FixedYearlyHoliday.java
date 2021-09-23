package com.kolejnik.bizdays.holiday;

import java.time.LocalDate;
import java.time.Month;

public class FixedYearlyHoliday extends YearlyHoliday {

    public static final Holiday NEW_YEAR = new FixedYearlyHoliday(Month.JANUARY, 1);
    public static final Holiday CHRISTMAS = new FixedYearlyHoliday(Month.DECEMBER, 25);
    public static final Holiday BOXING_DAY =  new FixedYearlyHoliday(Month.DECEMBER, 26);

    private Month month;
    private int dayOfMonth;

    public FixedYearlyHoliday(Month month, int dayOfMonth) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public LocalDate getByYear(int year) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return month.equals(date.getMonth()) && dayOfMonth == date.getDayOfMonth();
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
}
