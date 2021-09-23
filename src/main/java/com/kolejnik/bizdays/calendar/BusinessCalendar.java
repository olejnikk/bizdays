package com.kolejnik.bizdays.calendar;

import com.kolejnik.bizdays.BusinessDayCalculator;
import com.kolejnik.bizdays.InvalidHolidayException;
import com.kolejnik.bizdays.holiday.Holiday;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BusinessCalendar implements BusinessDayCalculator {

    // TODO: parametrize it
    /*
     * 4 years. If there is no holiday in MAX_BUSINESS_DAYS_BLOCK days
     * there must be something wrong with the calendar.
     */
    private static final int MAX_BUSINESS_DAYS_BLOCK = 1461;

    private final Set<Holiday> holidays = new HashSet<>();

    @Override
    public boolean isBusinessDay(LocalDate date) {
        return !isHoliday(date);
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        for (Holiday holiday : holidays) {
            if (holiday.isHoliday(date)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LocalDate nextBusinessDay() {
        return businessDayAfter(LocalDate.now());
    }

    @Override
    public LocalDate businessDayAfter(LocalDate date) {
        return nextBusinessDay(date, 1);
    }

    @Override
    public LocalDate businessDayBefore(LocalDate date) {
        return nextBusinessDay(date, -1);
    }

    private LocalDate nextBusinessDay(LocalDate date, int step) {
        int i = 0;
        do {
            date = date.plusDays(step);
            if (++i > MAX_BUSINESS_DAYS_BLOCK) {
                throw new InvalidHolidayException("No holiday found in "
                        + MAX_BUSINESS_DAYS_BLOCK + " days");
            }
        } while (!isBusinessDay(date));
        return date;
    }

    @Override
    public int businessDaysBetween(LocalDate from, LocalDate to) {
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("Invalid date range: " + from + " - " + to);
        }
        LocalDate date = from;
        LocalDate endDate = to.plusDays(1);
        int businessDaysCount = 0;
        while (date.isBefore(endDate)) {
            if (isBusinessDay(date)) {
                businessDaysCount++;
            }
            date = date.plusDays(1);
        }
        return businessDaysCount;
    }

    @Override
    public LocalDate plus(LocalDate date, int businessDaysCount) {
        LocalDate endDate = date;
        int step = businessDaysCount >= 0 ? 1 : -1;
        while (businessDaysCount != 0) {
            endDate = nextBusinessDay(endDate, step);
            businessDaysCount -= step;
        }
        return endDate;
    }

    @Override
    public LocalDate minus(LocalDate date, int businessDaysCount) {
        return plus(date, -businessDaysCount);
    }

    public boolean addHoliday(Holiday holiday) {
        return holidays.add(holiday);
    }

    public void addHolidays(Collection<Holiday> holidays) {
        this.holidays.addAll(holidays);
    }

    public boolean removeHoliday(Holiday holiday) {
        return holidays.remove(holiday);
    }

    public Set<Holiday> getHolidays() {
        return new HashSet<>(holidays);
    }

}
