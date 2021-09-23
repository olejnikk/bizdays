package com.kolejnik.bizdays.calendar;

import com.kolejnik.bizdays.holiday.CronHoliday;
import com.kolejnik.bizdays.holiday.FixedYearlyHoliday;
import com.kolejnik.bizdays.holiday.Holiday;

import java.time.Month;

public class AmericanBusinessCalendar extends BusinessCalendar {

    private static BusinessCalendar businessCalendar;

    public static final Holiday LUTER_KING_BDAY = new CronHoliday("* * * ? JAN MON#3 *");
    public static final Holiday WASHINGTON_BDAY = new CronHoliday("* * * ? FEB MON#3 *");
    public static final Holiday MEMORIAL_DAY = new CronHoliday("* * * ? MAY 2L *");
    public static final Holiday INDEPENDENCE_DAY = new FixedYearlyHoliday(Month.JULY, 4);
    public static final Holiday LABOR_DAY = new CronHoliday("* * * ? SEP MON#1 *");
    public static final Holiday COLUMBUS_DAY = new CronHoliday("* * * ? OCT MON#2 *");
    public static final Holiday VETERANS_DAY = new FixedYearlyHoliday(Month.NOVEMBER, 11);
    public static final Holiday THANKSGIVING = new CronHoliday("* * * ? NOV THU#4 *");

    public static BusinessCalendar getInstance() {
        if (businessCalendar != null) {
            return businessCalendar;
        }
        businessCalendar = new BusinessCalendar();
        businessCalendar.addHoliday(CronHoliday.SATURDAY);
        businessCalendar.addHoliday(CronHoliday.SUNDAY);
        businessCalendar.addHoliday(FixedYearlyHoliday.NEW_YEAR);
        businessCalendar.addHoliday(LUTER_KING_BDAY);
        businessCalendar.addHoliday(WASHINGTON_BDAY);
        businessCalendar.addHoliday(MEMORIAL_DAY);
        businessCalendar.addHoliday(INDEPENDENCE_DAY);
        businessCalendar.addHoliday(LABOR_DAY);
        businessCalendar.addHoliday(COLUMBUS_DAY);
        businessCalendar.addHoliday(VETERANS_DAY);
        businessCalendar.addHoliday(THANKSGIVING);
        businessCalendar.addHoliday(FixedYearlyHoliday.CHRISTMAS);

        return businessCalendar;
    }

}
