package com.kolejnik.bizdays.schedule;

import com.kolejnik.bizdays.BusinessDayCalculator;
import com.kolejnik.bizdays.BusinessTimeCalculator;
import com.kolejnik.bizdays.calendar.BusinessCalendar;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BusinessSchedule implements BusinessTimeCalculator {

    private BusinessDayCalculator businessCalendar;

    private BusinessDay defaultBusinessDay = new BusinessDay(8, 0, 16, 0);

    private Map<DayOfWeek, BusinessDay> businessDays;

    public BusinessSchedule(BusinessCalendar calendar) {
        this.businessCalendar = calendar;
    }

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
        return plus(dateTime, Duration.ofHours(hoursToAdd));
    }

    @Override
    public LocalDateTime minus(LocalDateTime dateTime, int hoursToSubtract) {
        return minus(dateTime, Duration.ofHours(hoursToSubtract));
    }

    @Override
    public LocalDateTime plus(LocalDateTime dateTime, Duration duration) {
        if (duration.isNegative()) {
            return minus(dateTime, duration.negated());
        }
        while (!duration.isZero()) {
            if (!isBusinessTime(dateTime)) {
                dateTime = businessDayStartAfter(dateTime);
            }

            BusinessDay businessDay = getBusinessDay(dateTime);
            Duration timeLeft = Duration.between(dateTime.toLocalTime(), businessDay.getEndTime());
            if (duration.compareTo(timeLeft) < 0) {
                return dateTime.plus(duration);
            }
            duration = duration.minus(timeLeft);
            dateTime = businessDayStartAfter(dateTime);
        }

        return dateTime;
    }

    @Override
    public LocalDateTime minus(LocalDateTime dateTime, Duration duration) {
        if (duration.isNegative()) {
            return plus(dateTime, duration.negated());
        }

        while (!duration.isZero()) {
            if (!isBusinessTime(dateTime)) {
                dateTime = businessDayEndBefore(dateTime);
            }

            BusinessDay businessDay = getBusinessDay(dateTime);
            Duration timeLeft = Duration.between(businessDay.getStartTime(), dateTime.toLocalTime());
            if (duration.compareTo(timeLeft) < 0) {
                return dateTime.minus(duration);
            }
            duration = duration.minus(timeLeft);
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

    public BusinessDay putBusinessDay(DayOfWeek dayOfWeek, BusinessDay businessDay) {
        if (businessDays == null) {
            businessDays = new HashMap<>();
        }
        return businessDays.put(dayOfWeek, businessDay);
    }

    public BusinessDay removeBusinessDay(DayOfWeek dayOfWeek) {
        if (businessDays != null) {
            return businessDays.remove(dayOfWeek);
        }
        return null;
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
