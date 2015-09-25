package com.kolejnik;

import com.kolejnik.bizdays.calendar.BusinessCalendarFactory;
import com.kolejnik.bizdays.calendar.PolishBusinessCalendarFactory;
import com.kolejnik.bizdays.schedule.BusinessDay;
import com.kolejnik.bizdays.schedule.BusinessSchedule;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BusinessScheduleTest {

    private BusinessCalendarFactory pbcf = new PolishBusinessCalendarFactory();
    private BusinessSchedule schedule = new BusinessSchedule(pbcf.getInstance());

    {
        schedule.putBusinessDay(DayOfWeek.MONDAY, new BusinessDay(LocalTime.of(8, 0), LocalTime.of(16, 0)));
        schedule.putBusinessDay(DayOfWeek.TUESDAY, new BusinessDay(LocalTime.of(10, 0), LocalTime.of(14, 0)));
        schedule.putBusinessDay(DayOfWeek.WEDNESDAY, new BusinessDay(LocalTime.of(8, 0), LocalTime.of(16, 0)));
        schedule.putBusinessDay(DayOfWeek.THURSDAY, new BusinessDay(LocalTime.of(8, 0), LocalTime.of(16, 0)));
        schedule.putBusinessDay(DayOfWeek.FRIDAY, new BusinessDay(LocalTime.of(7, 0), LocalTime.of(15, 0)));
    }

    @Test
    public void testIsBusinessTime() {
        assertFalse(schedule.isBusinessTime(LocalDateTime.of(2016, 1, 13, 7, 30)));
        assertTrue(schedule.isBusinessTime(LocalDateTime.of(2016, 1, 13, 8, 15)));
        assertFalse(schedule.isBusinessTime(LocalDateTime.of(2016, 1, 13, 16, 1)));
    }

    @Test
    public void testPlus() {
        LocalDateTime dateTime = LocalDateTime.of(2016, 1, 13, 15, 0);
        assertEquals(LocalDateTime.of(2016, 1, 14, 13, 0), schedule.plus(dateTime, Duration.ofHours(6)));
        assertEquals(LocalDateTime.of(2016, 1, 15, 12, 0), schedule.plus(dateTime, Duration.ofHours(14)));
    }

    @Test
    public void testMinus() {
        LocalDateTime dateTime = LocalDateTime.of(2016, 1, 13, 15, 0);
        assertEquals(LocalDateTime.of(2016, 1, 12, 12, 0), schedule.minus(dateTime, Duration.ofHours(9)));
        assertEquals(LocalDateTime.of(2016, 1, 4, 15, 0), schedule.minus(dateTime, Duration.ofHours(40)));
    }

}
