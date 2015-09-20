package com.kolejnik.bizdays.schedule;

import com.kolejnik.bizdays.BusinessDayCalculator;
import com.kolejnik.bizdays.BusinessTimeCalculator;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class BusinessSchedule implements BusinessTimeCalculator {

    private BusinessDayCalculator businessCalendar;

    private BusinessDay defaultBusinessDay = new BusinessDay(8, 0, 16, 0);

    private Map<DayOfWeek, BusinessDay> businessDays;

    @Override
    public boolean isBusinessTime(LocalDateTime dateTime) {
        if (!businessCalendar.isBusinessDay(dateTime.toLocalDate())) {
            return false;
        }
        BusinessDay businessDay = getBusinessDay(dateTime);
        return businessDay.contains(dateTime.toLocalTime());
    }

    @Override
    public LocalDateTime plus(LocalDateTime dateTime, int hoursToAdd) {
        if (hoursToAdd < 0) {
            return minus(dateTime, -hoursToAdd);
        }
        long secondsToAdd = hoursToAdd * 3600;
        while (secondsToAdd >= 0) {
            if (!isBusinessTime(dateTime)) {
                dateTime = businessDayStartAfter(dateTime);
            }

            BusinessDay businessDay = getBusinessDay(dateTime);
            long secondsLeft = Duration.between(dateTime.toLocalTime(),
                    businessDay.getEndTime()).get(ChronoUnit.SECONDS);
            if (secondsToAdd <= secondsLeft) {
                return dateTime.plusSeconds(secondsToAdd);
            }
            secondsToAdd -= secondsLeft;
            dateTime = businessDayStartAfter(dateTime);
        }

        return dateTime;
    }

    @Override
    public LocalDateTime minus(LocalDateTime dateTime, int hoursToSubtract) {
        if (hoursToSubtract < 0) {
            return plus(dateTime, -hoursToSubtract);
        }
        long secondsToSubstract = hoursToSubtract * 3600;
        while (secondsToSubstract >= 0) {
            if (!isBusinessTime(dateTime)) {
                dateTime = businessDayEndBefore(dateTime);
            }

            BusinessDay businessDay = getBusinessDay(dateTime);
            long secondsLeft = Duration.between(businessDay.getStartTime(),
                    dateTime.toLocalTime()).get(ChronoUnit.SECONDS);
            if (secondsToSubstract <= secondsLeft) {
                return dateTime.minusSeconds(secondsToSubstract);
            }
            secondsToSubstract -= secondsLeft;
            dateTime = businessDayEndBefore(dateTime);
        }

        return dateTime;
    }

    @Override
    public LocalDateTime businessDayStartAfter(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();

        if (!businessCalendar.isBusinessDay(date) || !isBeforeBusinessDayStart(dateTime)) {
            date = businessCalendar.businessDayAfter(date);
        }

        BusinessDay businessDay = getBusinessDay(date);
        return LocalDateTime.of(date, businessDay.getStartTime());
    }

    @Override
    public LocalDateTime businessDayEndAfter(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();

        if (!businessCalendar.isBusinessDay(date) || !isBeforeBusinessDayEnd(dateTime)) {
            date = businessCalendar.businessDayAfter(date);
        }

        BusinessDay businessDay = getBusinessDay(date);
        return LocalDateTime.of(date, businessDay.getEndTime());
    }

    @Override
    public LocalDateTime businessDayStartBefore(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();

        if (!businessCalendar.isBusinessDay(date) || isBeforeBusinessDayStart(dateTime)) {
            date = businessCalendar.businessDayBefore(date);
        }

        BusinessDay businessDay = getBusinessDay(date);
        return LocalDateTime.of(date, businessDay.getStartTime());
    }

    @Override
    public LocalDateTime businessDayEndBefore(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();

        if (!businessCalendar.isBusinessDay(date) || isBeforeBusinessDayEnd(dateTime)) {
            date = businessCalendar.businessDayBefore(date);
        }

        BusinessDay businessDay = getBusinessDay(date);
        return LocalDateTime.of(date, businessDay.getEndTime());
    }

    private boolean isBeforeBusinessDayEnd(LocalDateTime dateTime) {
        BusinessDay businessDay = getBusinessDay(dateTime.toLocalDate());
        return businessDay.getEndTime().equals(dateTime.toLocalTime()) ||
                businessDay.getEndTime().isAfter(dateTime.toLocalTime());
    }

    private boolean isBeforeBusinessDayStart(LocalDateTime dateTime) {
        BusinessDay businessDay = getBusinessDay(dateTime.toLocalDate());
        return businessDay.getStartTime().isAfter(dateTime.toLocalTime());
    }

    private BusinessDay getBusinessDay(LocalDateTime dateTime) {
        return getBusinessDay(dateTime.toLocalDate());
    }

    private BusinessDay getBusinessDay(LocalDate date) {
        if (businessDays != null) {
            BusinessDay businessDay = businessDays.get(date.getDayOfWeek());
            if (businessDay != null) {
                return businessDay;
            }
        }
        return defaultBusinessDay;
    }

    private BusinessDay putBusinessDay(DayOfWeek dayOfWeek, BusinessDay businessDay) {
        if (businessDays == null) {
            businessDays = new HashMap<>();
        }
        return businessDays.put(dayOfWeek, businessDay);
    }

    private BusinessDay removeBusinessDay(DayOfWeek dayOfWeek) {
        if (businessDays != null) {
            return businessDays.remove(dayOfWeek);
        }
        return null;
    }

    public BusinessDay getDefaultBusinessDay() {
        return defaultBusinessDay;
    }

    public void setDefaultBusinessDay(BusinessDay defaultBusinessDay) {
        this.defaultBusinessDay = defaultBusinessDay;
    }

    public Map<DayOfWeek, BusinessDay> getBusinessDays() {
        return businessDays;
    }

    public void setBusinessDays(Map<DayOfWeek, BusinessDay> businessDays) {
        this.businessDays = businessDays;
    }

    public BusinessDayCalculator getBusinessCalendar() {
        return businessCalendar;
    }

    public void setBusinessCalendar(BusinessDayCalculator businessCalendar) {
        this.businessCalendar = businessCalendar;
    }
}
