package com.kolejnik.bizdays;

import com.kolejnik.bizdays.calendar.BusinessCalendar;
import com.kolejnik.bizdays.calendar.BusinessCalendarFactory;
import com.kolejnik.bizdays.calendar.PolishBusinessCalendarFactory;

import java.text.ParseException;
import java.time.LocalDate;

public class Demo {
    public static void main(String[] args) throws ParseException {
        BusinessCalendarFactory pbcf = new PolishBusinessCalendarFactory();
        BusinessCalendar calendar = pbcf.getInstance();

        System.out.println("Next business day: " + calendar.nextBusinessDay());

        int days = calendar.businessDaysBetween(LocalDate.of(2016, 1, 4), LocalDate.of(2016, 12, 31));
        System.out.println("Business days in 2016: " + days);
    }
}
