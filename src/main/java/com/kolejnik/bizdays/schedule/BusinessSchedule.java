package com.kolejnik.bizdays.schedule;

import com.kolejnik.bizdays.BusinessDayCalculator;
import com.kolejnik.bizdays.BusinessTimeCalculator;
import com.kolejnik.bizdays.calendar.BusinessCalendar;

import java.time.*;
import java.util.EnumMap;
import java.util.Map;

public class BusinessSchedule implements BusinessTimeCalculator {

    private final BusinessDayCalculator businessCalendar;
    private final BusinessDay defaultBusinessDay = new BusinessDay(8, 0, 16, 0);
    private final Map<DayOfWeek, BusinessDay> businessDays = new EnumMap<>(DayOfWeek.class);

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
            if (!businessCalendar.isBusinessDay(dateTime.toLocalDate())) {
                dateTime = businessDayStartAfter(dateTime);
            } else {
                BusinessDay businessDay = getBusinessDay(dateTime);
                LocalTime lt = dateTime.toLocalTime();
                LocalDate date = dateTime.toLocalDate();

                if (lt.isBefore(businessDay.getStartTime()))
                    dateTime = LocalDateTime.of(date, businessDay.getStartTime());
                else if (businessDay.hasBreak() && lt.isBefore(businessDay.getBreakEndTime()) && (lt.compareTo(businessDay.getBreakStartTime()) >= 0))
                    dateTime = LocalDateTime.of(date, businessDay.getBreakEndTime());
                else if (lt.compareTo(businessDay.getEndTime()) >= 0)
                    dateTime = businessDayStartAfter(dateTime);
            }

            BusinessDay businessDay = getBusinessDay(dateTime);
            LocalTime timeLocal = dateTime.toLocalTime();
            Duration timeLeft;
            if (businessDay.hasBreak() && timeLocal.isBefore(businessDay.getBreakStartTime())) {
                timeLeft = Duration.between(dateTime.toLocalTime(), businessDay.getBreakStartTime())
                        .plus(Duration.between(businessDay.getBreakEndTime(), businessDay.getEndTime()));
            } else if (businessDay.hasBreak() && timeLocal.isAfter(businessDay.getBreakStartTime()) && timeLocal.isBefore(businessDay.getBreakEndTime())) {
                timeLeft = Duration.between(businessDay.getBreakEndTime(), businessDay.getEndTime());
            } else {
                timeLeft = Duration.between(dateTime.toLocalTime(), businessDay.getEndTime());
            }
            if (duration.compareTo(timeLeft) < 0) {
                LocalDateTime future = dateTime.plus(duration);
                if (businessDay.hasBreak()) {
                    LocalTime flt = future.toLocalTime();
                    if (flt.isAfter(businessDay.getBreakStartTime()))
                        future = future.plus(Duration.between(businessDay.getBreakStartTime(), businessDay.getBreakEndTime()));
                }
                return future;
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
            if (!businessCalendar.isBusinessDay(dateTime.toLocalDate())) {
                dateTime = businessDayEndBefore(dateTime);
            } else {
                BusinessDay businessDay = getBusinessDay(dateTime);
                LocalTime lt = dateTime.toLocalTime();
                LocalDate date = dateTime.toLocalDate();

                if (lt.isAfter(businessDay.getEndTime()))
                    dateTime = LocalDateTime.of(date, businessDay.getEndTime());
                else if (businessDay.hasBreak() && lt.isAfter(businessDay.getBreakStartTime()) && (lt.compareTo(businessDay.getBreakEndTime()) <= 0))
                    dateTime = LocalDateTime.of(date, businessDay.getBreakStartTime());
                else if (lt.compareTo(businessDay.getStartTime()) <= 0)
                    dateTime = businessDayEndBefore(dateTime);
            }

            BusinessDay businessDay = getBusinessDay(dateTime);

            LocalTime timeLocal = dateTime.toLocalTime();
            Duration timeLeft;
            if (businessDay.hasBreak() && timeLocal.isAfter(businessDay.getBreakEndTime())) {
                timeLeft = Duration.between(businessDay.getBreakEndTime(), timeLocal)
                        .plus(Duration.between(businessDay.getStartTime(), businessDay.getBreakStartTime()));
            } else if (businessDay.hasBreak() && timeLocal.isAfter(businessDay.getBreakStartTime()) && timeLocal.isBefore(businessDay.getBreakEndTime())) {
                timeLeft = Duration.between(businessDay.getStartTime(), businessDay.getBreakStartTime());
            } else {
                timeLeft = Duration.between(businessDay.getStartTime(), timeLocal);
            }

            if (duration.compareTo(timeLeft) < 0) {
                LocalDateTime past = dateTime.minus(duration);
                if (businessDay.hasBreak()) {
                    LocalTime flt = past.toLocalTime();
                    if (flt.isBefore(businessDay.getBreakEndTime()))
                        past = past.minus(Duration.between(businessDay.getBreakStartTime(), businessDay.getBreakEndTime()));
                }
                return past;

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
        return businessDays.put(dayOfWeek, businessDay);
    }

    public void putBusinessDays(Map<DayOfWeek, BusinessDay> businessDays) {
        businessDays.putAll(businessDays);
    }

    public BusinessDay removeBusinessDay(DayOfWeek dayOfWeek) {
        return businessDays.remove(dayOfWeek);
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
        BusinessDay businessDay = businessDays.get(date.getDayOfWeek());
        if (businessDay != null) {
            return businessDay;
        }
        return defaultBusinessDay;
    }

    public BusinessDay getDefaultBusinessDay() {
        return defaultBusinessDay;
    }

    public Map<DayOfWeek, BusinessDay> getBusinessDays() {
        return businessDays;
    }

    public BusinessDayCalculator getBusinessCalendar() {
        return businessCalendar;
    }

}
