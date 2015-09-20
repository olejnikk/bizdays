package com.kolejnik.bizdays.schedule;

import com.kolejnik.bizdays.BusinessDayCalculator;
import com.kolejnik.bizdays.BusinessTimeCalculator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.*;
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
        while (secondsToAdd > 0) {
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
        throw new NotImplementedException();
    }

    @Override
    public LocalDateTime businessDayStartAfter(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();

        if (businessCalendar.isBusinessDay(date)) {
            BusinessDay businessDay = getBusinessDay(date);
            if (businessDay.getStartTime().isAfter(dateTime.toLocalTime())) {
                return LocalDateTime.of(dateTime.toLocalDate(), businessDay.getStartTime());
            }
        }

        date = businessCalendar.businessDayAfter(date);
        BusinessDay businessDay = getBusinessDay(date);
        return LocalDateTime.of(date, businessDay.getStartTime());
    }

    @Override
    public LocalDateTime businessDayEndAfter(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();

        if (businessCalendar.isBusinessDay(date)) {
            BusinessDay businessDay = getBusinessDay(date);
            if (businessDay.getEndTime().isAfter(dateTime.toLocalTime())) {
                return LocalDateTime.of(dateTime.toLocalDate(), businessDay.getEndTime());
            }
        }

        date = businessCalendar.businessDayAfter(date);
        BusinessDay businessDay = getBusinessDay(date);
        return LocalDateTime.of(date, businessDay.getEndTime());
    }

    private LocalTime getBusinessDayEnd(LocalDate date) {
        BusinessDay businessDay = getBusinessDay(date);
        return businessDay.getEndTime();
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
