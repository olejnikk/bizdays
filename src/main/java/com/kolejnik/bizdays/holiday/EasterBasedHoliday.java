package com.kolejnik.bizdays.holiday;

import java.time.LocalDate;

/*
 * GREGORIAN CALENDAR ONLY!!
 */
public class EasterBasedHoliday extends MovableYearlyHoliday {

    public static EasterBasedHoliday EASTER = new EasterBasedHoliday();
    public static EasterBasedHoliday EASTER_MONDAY = new EasterBasedHoliday() {
        @Override
        public LocalDate calculateByYear(int year) {
            LocalDate easterDay = EASTER.getByYear(year);
            return easterDay.plusDays(1);
        }
    };
    public static EasterBasedHoliday CORPUS_CHRISTI = new EasterBasedHoliday() {
        @Override
        public LocalDate calculateByYear(int year) {
            LocalDate easterDay = EASTER.getByYear(year);
            return easterDay.plusDays(60);
        }
    };
    public static EasterBasedHoliday GREEN_WEEK = new EasterBasedHoliday() {
        @Override
        public LocalDate calculateByYear(int year) {
            LocalDate easterDay = EASTER.getByYear(year);
            return easterDay.plusDays(49);
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
        int p = (h + l - 7 * m + 114) % 31;

        int day = p + 1;
        int month = (h + l - 7 * m + 114) / 31;

        return LocalDate.of(year, month, day);
    }
}
