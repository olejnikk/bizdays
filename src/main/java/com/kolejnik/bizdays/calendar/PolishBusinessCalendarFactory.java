package com.kolejnik.bizdays.calendar;

import com.kolejnik.bizdays.holiday.CronHoliday;
import com.kolejnik.bizdays.holiday.EasterBasedHoliday;
import com.kolejnik.bizdays.holiday.FixedYearlyHoliday;
import com.kolejnik.bizdays.holiday.Holiday;

import java.time.Month;

public class PolishBusinessCalendarFactory implements BusinessCalendarFactory {

    public static Holiday EPIPHANY = new FixedYearlyHoliday(Month.JANUARY, 6);
    public static Holiday WORKERS_DAY = new FixedYearlyHoliday(Month.MAY, 1);
    public static Holiday MAY_3RD_CONSTITUTION_DAY = new FixedYearlyHoliday(Month.MAY, 3);
    public static Holiday ASSUMPTION = new FixedYearlyHoliday(Month.AUGUST, 15);
    public static Holiday ALL_SAINTS_DAY = new FixedYearlyHoliday(Month.NOVEMBER, 1);
    public static Holiday INDEPENDENCE_DAY = new FixedYearlyHoliday(Month.NOVEMBER, 11);

    private static BusinessCalendar polishBusinessCalendar;

    public BusinessCalendar getInstance() {
        if (PolishBusinessCalendarFactory.polishBusinessCalendar != null) {
            return PolishBusinessCalendarFactory.polishBusinessCalendar;
        }
        BusinessCalendar polishBusinessCalendar = new BusinessCalendar();
        polishBusinessCalendar.addHoliday(CronHoliday.SATURDAY);
        polishBusinessCalendar.addHoliday(CronHoliday.SUNDAY);
        polishBusinessCalendar.addHoliday(FixedYearlyHoliday.NEW_YEAR);
        polishBusinessCalendar.addHoliday(FixedYearlyHoliday.CHRISTMAS);
        polishBusinessCalendar.addHoliday(FixedYearlyHoliday.BOXING_DAY);
        polishBusinessCalendar.addHoliday(EasterBasedHoliday.EASTER);
        polishBusinessCalendar.addHoliday(EasterBasedHoliday.EASTER_MONDAY);
        polishBusinessCalendar.addHoliday(EasterBasedHoliday.CORPUS_CHRISTI);
        polishBusinessCalendar.addHoliday(EasterBasedHoliday.GREEN_WEEK);
        polishBusinessCalendar.addHoliday(EPIPHANY);
        polishBusinessCalendar.addHoliday(WORKERS_DAY);
        polishBusinessCalendar.addHoliday(MAY_3RD_CONSTITUTION_DAY);
        polishBusinessCalendar.addHoliday(ASSUMPTION);
        polishBusinessCalendar.addHoliday(ALL_SAINTS_DAY);
        polishBusinessCalendar.addHoliday(INDEPENDENCE_DAY);
        return polishBusinessCalendar;
    }
}
