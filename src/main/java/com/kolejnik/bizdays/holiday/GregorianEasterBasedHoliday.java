package com.kolejnik.bizdays.holiday;

import java.time.LocalDate;

public class GregorianEasterBasedHoliday extends MovableYearlyHoliday {

    public static final GregorianEasterBasedHoliday EASTER = new GregorianEasterBasedHoliday();
    public static final GregorianEasterBasedHoliday EASTER_MONDAY = new GregorianEasterBasedHoliday() {
        @Override
        public LocalDate calculateByYear(int year) {
            LocalDate easterDay = EASTER.getByYear(year);
            return easterDay.plusDays(1);
        }
    };
    public static final GregorianEasterBasedHoliday CORPUS_CHRISTI = new GregorianEasterBasedHoliday() {
        @Override
        public LocalDate calculateByYear(int year) {
            LocalDate easterDay = EASTER.getByYear(year);
            return easterDay.plusDays(60);
        }
    };
    public static final GregorianEasterBasedHoliday GREEN_WEEK = new GregorianEasterBasedHoliday() {
        @Override
        public LocalDate calculateByYear(int year) {
            LocalDate easterDay = EASTER.getByYear(year);
            return easterDay.plusDays(49);
        }
    };
    public static final GregorianEasterBasedHoliday GOOD_FRIDAY = new GregorianEasterBasedHoliday() {
        @Override
        public LocalDate calculateByYear(int year) {
            LocalDate easterDay = EASTER.getByYear(year);
            return easterDay.minusDays(2);
        }
    };

    @Override
    public LocalDate calculateByYear(int year) {
        return calculateMeeusJonesButcher(year);
    }

    private static LocalDate calculateMeeusJonesButcher(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int n = h + l - 7 * m + 114;
        int o = n % 31;

        int day = o + 1;
        int month = n / 31;

        return LocalDate.of(year, month, day);
    }
}
