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

    private static BusinessCalendar businessCalendar;

    public BusinessCalendar getInstance() {
        if (businessCalendar != null) {
            return businessCalendar;
        }
        businessCalendar = new BusinessCalendar();
        businessCalendar.addHoliday(CronHoliday.SATURDAY);
        businessCalendar.addHoliday(CronHoliday.SUNDAY);
        businessCalendar.addHoliday(FixedYearlyHoliday.NEW_YEAR);
        businessCalendar.addHoliday(FixedYearlyHoliday.CHRISTMAS);
        businessCalendar.addHoliday(FixedYearlyHoliday.BOXING_DAY);
        businessCalendar.addHoliday(EasterBasedHoliday.EASTER);
        businessCalendar.addHoliday(EasterBasedHoliday.EASTER_MONDAY);
        businessCalendar.addHoliday(EasterBasedHoliday.CORPUS_CHRISTI);
        businessCalendar.addHoliday(EasterBasedHoliday.GREEN_WEEK);
        businessCalendar.addHoliday(EPIPHANY);
        businessCalendar.addHoliday(WORKERS_DAY);
        businessCalendar.addHoliday(MAY_3RD_CONSTITUTION_DAY);
        businessCalendar.addHoliday(ASSUMPTION);
        businessCalendar.addHoliday(ALL_SAINTS_DAY);
        businessCalendar.addHoliday(INDEPENDENCE_DAY);
        return businessCalendar;
    }
}
