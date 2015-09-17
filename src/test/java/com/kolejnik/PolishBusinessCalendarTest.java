package com.kolejnik;

import com.kolejnik.bizdays.calendar.BusinessCalendar;
import com.kolejnik.bizdays.calendar.BusinessCalendarFactory;
import com.kolejnik.bizdays.calendar.PolishBusinessCalendarFactory;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*
    TODO: add tests
 */
public class PolishBusinessCalendarTest {

    private BusinessCalendarFactory pbcf = new PolishBusinessCalendarFactory();

    private static String[] easterDates = {"1980-04-06", "1988-04-03", "2002-03-31", "2004-04-11",
            "2010-04-04", "2015-04-05", "2016-03-27", "2017-04-16", "2018-04-01", "2019-04-21",
            "2020-04-12", "2021-04-04", "2022-04-17", "2023-04-09", "2024-03-31", "2025-04-20"};

    private static String[] easterMondayDates = {"1980-04-07", "1988-04-04", "2002-04-01", "2004-04-12",
            "2010-04-05", "2015-04-06", "2016-03-28", "2017-04-17", "2018-04-02", "2019-04-22",
            "2020-04-13", "2021-04-05", "2022-04-18", "2023-04-10", "2024-04-01", "2025-04-21"};


//    @Test
//    public void easterTest() {
//        for (String easterStr : easterDates) {
//            LocalDate easter = LocalDate.parse(easterStr);
//            System.out.println("Testing easter " + easter);
//            assertTrue(EasterBasedHoliday.EASTER.isHoliday(easter));
//            assertFalse(EasterBasedHoliday.EASTER.isHoliday(easter.plusDays(randomStep())));
//            assertFalse(EasterBasedHoliday.EASTER.isHoliday(easter.minusDays(randomStep())));
//        }
//    }
//
//    @Test
//    public void testEasterMonday() {
//        for (String easterMondayStr : easterMondayDates) {
//            LocalDate easterMonday = LocalDate.parse(easterMondayStr);
//            System.out.println("Testing easter monday " + easterMonday);
//            assertTrue(EasterBasedHoliday.EASTER_MONDAY.isHoliday(easterMonday));
//            assertFalse(EasterBasedHoliday.EASTER_MONDAY.isHoliday(easterMonday.plusDays(randomStep())));
//            assertFalse(EasterBasedHoliday.EASTER_MONDAY.isHoliday(easterMonday.minusDays(randomStep())));
//        }
//    }

    @Test
    public void koTest() {
        BusinessCalendar calendar = pbcf.getInstance();
        System.out.println("NBD: " + calendar.nextBusinessDay());
        assertTrue(true);
    }

    private int randomStep() {
        return (int) (365 * Math.random()) + 1;
    }

//    public PoliskWorkCalendarTest(String testName) {
//        super(testName);
//    }
//
//    public static Test suite() {
//        return new TestSuite(PoliskWorkCalendarTest.class);
//    }

}
