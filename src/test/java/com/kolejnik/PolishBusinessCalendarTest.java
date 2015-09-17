package com.kolejnik;

import com.kolejnik.bizdays.calendar.BusinessCalendarFactory;
import com.kolejnik.bizdays.calendar.PolishBusinessCalendarFactory;
import com.kolejnik.bizdays.holiday.CronHoliday;
import com.kolejnik.bizdays.holiday.EasterBasedHoliday;
import com.kolejnik.bizdays.holiday.FixedYearlyHoliday;
import com.kolejnik.bizdays.holiday.Holiday;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PolishBusinessCalendarTest {

    private BusinessCalendarFactory pbcf = new PolishBusinessCalendarFactory();

    private static String[] easterDates = {"1980-04-06", "1988-04-03", "2002-03-31", "2004-04-11",
            "2010-04-04", "2015-04-05", "2016-03-27", "2017-04-16", "2018-04-01", "2019-04-21",
            "2020-04-12", "2021-04-04", "2022-04-17", "2023-04-09", "2024-03-31", "2025-04-20"};

    private static String[] easterMondayDates = {"1980-04-07", "1988-04-04", "2002-04-01", "2004-04-12",
            "2010-04-05", "2015-04-06", "2016-03-28", "2017-04-17", "2018-04-02", "2019-04-22",
            "2020-04-13", "2021-04-05", "2022-04-18", "2023-04-10", "2024-04-01", "2025-04-21"};

    private static String[] corpusChristiDates = {"1980-06-05", "1988-06-02", "2002-05-30", "2004-06-10",
            "2010-06-03", "2015-06-04", "2016-05-26", "2017-06-15", "2018-05-31", "2019-06-20",
            "2020-06-11", "2021-06-03", "2022-06-16", "2023-06-08", "2024-05-30", "2025-06-19"};

    private static String[] newYearDates = {"1980-01-01", "1988-01-01", "2002-01-01", "2004-01-01",
            "2010-01-01", "2015-01-01", "2016-01-01", "2017-01-01", "2018-01-01", "2019-01-01",
            "2020-01-01", "2021-01-01", "2022-01-01", "2023-01-01", "2024-01-01", "2025-01-01"};

    private static String[] sundayDates = {"2015-01-04", "2015-02-15", "2015-03-22", "2015-04-26", "2015-05-31",
            "2015-06-07", "2015-07-12", "2015-08-23", "2015-09-27", "2015-10-04", "2015-11-08", "2015-12-20"};

    @Test
    public void easterTest() {
        testYearlyHoliday(EasterBasedHoliday.EASTER, "Easter", easterDates);
    }

    @Test
    public void testEasterMonday() {
        testYearlyHoliday(EasterBasedHoliday.EASTER_MONDAY, "Easter monday", easterMondayDates);
    }

    @Test
    public void testCorpusChristi() {
        testYearlyHoliday(EasterBasedHoliday.CORPUS_CHRISTI, "Corpus Christi", corpusChristiDates);
    }

    @Test
    public void testFixedHoliday() {
        testYearlyHoliday(FixedYearlyHoliday.NEW_YEAR, "New Year", newYearDates);
    }

    @Test
    public void testCronHoliday() {
        testHoliday(CronHoliday.SUNDAY, "Sunday", sundayDates);
    }

    private void testYearlyHoliday(Holiday holiday, String holidayName, String[] dates) {
        for (String dateStr : dates) {
            LocalDate date = LocalDate.parse(dateStr);
            assertTrue(holiday.isHoliday(date));
            assertFalse(holiday.isHoliday(date.plusDays(1)));
            assertFalse(holiday.isHoliday(date.minusDays(1)));
        }
    }

    private void testHoliday(Holiday holiday, String holidayName, String[] dates) {
        for (String dateStr : dates) {
            LocalDate date = LocalDate.parse(dateStr);
            assertTrue(holiday.isHoliday(date));
        }
    }

}
