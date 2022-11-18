package com.kolejnik;

import com.kolejnik.bizdays.calendar.PolishBusinessCalendar;
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

    private BusinessSchedule schedule = new BusinessSchedule(PolishBusinessCalendar.getInstance());

    {
        schedule.putBusinessDay(DayOfWeek.MONDAY, new BusinessDay(LocalTime.of(8, 0), LocalTime.of(17, 0), LocalTime.of(12, 0), LocalTime.of(13, 0)));
        schedule.putBusinessDay(DayOfWeek.TUESDAY, new BusinessDay(LocalTime.of(10, 0), LocalTime.of(15, 0), LocalTime.of(11, 0), LocalTime.of(12, 0)));
        schedule.putBusinessDay(DayOfWeek.WEDNESDAY, new BusinessDay(LocalTime.of(8, 0), LocalTime.of(17, 0), LocalTime.of(12, 0), LocalTime.of(13, 0)));
        schedule.putBusinessDay(DayOfWeek.THURSDAY, new BusinessDay(LocalTime.of(8, 0), LocalTime.of(17, 0), LocalTime.of(13, 0), LocalTime.of(14, 0)));
        schedule.putBusinessDay(DayOfWeek.FRIDAY, new BusinessDay(LocalTime.of(7, 0), LocalTime.of(16, 0), LocalTime.of(9, 0), LocalTime.of(10, 0)));
    }

    @Test
    public void testIsBusinessTime() {
        // check friday
        assertFalse(schedule.isBusinessTime(LocalDateTime.of(2022, 11, 18, 6, 30)));
        assertTrue(schedule.isBusinessTime(LocalDateTime.of(2022, 11, 18, 8, 15)));
        assertFalse(schedule.isBusinessTime(LocalDateTime.of(2022, 11, 18, 16, 1)));
        // break time
        assertFalse(schedule.isBusinessTime(LocalDateTime.of(2022, 11, 18, 9, 45)));
    }

    @Test
    public void testPlus() {
        LocalDateTime dateTime = LocalDateTime.of(2022, 11, 17, 15, 0);
        // check thu->fri
        assertEquals(LocalDateTime.of(2022, 11, 18, 11, 0), schedule.plus(dateTime, Duration.ofHours(6)));
        // check thu->mon
        assertEquals(LocalDateTime.of(2022, 11, 21, 14, 0), schedule.plus(dateTime, Duration.ofHours(15)));
    }

    @Test
    public void testMinus() {
        LocalDateTime dateTime = LocalDateTime.of(2022, 11, 17, 15, 0);
        // thu -> wed
        assertEquals(LocalDateTime.of(2022, 11, 12, 8, 0), schedule.minus(dateTime, Duration.ofHours(9)));
        assertEquals(LocalDateTime.of(2016, 1, 4, 15, 0), schedule.minus(dateTime, Duration.ofHours(40)));
    }

}
