package com.kolejnik.bizdays.calendar;

import com.kolejnik.bizdays.holiday.CronHoliday;
import com.kolejnik.bizdays.holiday.GregorianEasterBasedHoliday;
import com.kolejnik.bizdays.holiday.FixedYearlyHoliday;
import com.kolejnik.bizdays.holiday.Holiday;

public class BritishBusinessCalendar extends BusinessCalendar {

    public static final Holiday MAY_DAY_BANK = new CronHoliday("* * * ? MAY MON#1 *");
    public static final Holiday SPRING_BANK = new CronHoliday("* * * ? MAY 2L *");
    public static final Holiday LATE_SUMMER_BANK = new CronHoliday("* * * ? AUG 2L *");

    private static BusinessCalendar businessCalendar;

    public static BusinessCalendar getInstance() {
        if (businessCalendar != null) {
            return businessCalendar;
        }
        businessCalendar = new BusinessCalendar();
        businessCalendar.addHoliday(CronHoliday.SATURDAY);
        businessCalendar.addHoliday(CronHoliday.SUNDAY);
        businessCalendar.addHoliday(FixedYearlyHoliday.NEW_YEAR);
        businessCalendar.addHoliday(GregorianEasterBasedHoliday.GOOD_FRIDAY);
        businessCalendar.addHoliday(GregorianEasterBasedHoliday.EASTER_MONDAY);
        businessCalendar.addHoliday(MAY_DAY_BANK);
        businessCalendar.addHoliday(SPRING_BANK);
        businessCalendar.addHoliday(LATE_SUMMER_BANK);
        businessCalendar.addHoliday(FixedYearlyHoliday.CHRISTMAS);
        businessCalendar.addHoliday(FixedYearlyHoliday.BOXING_DAY);

        return businessCalendar;
    }

}
