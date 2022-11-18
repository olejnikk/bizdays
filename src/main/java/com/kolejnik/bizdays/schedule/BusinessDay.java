package com.kolejnik.bizdays.schedule;

import java.time.Duration;
import java.time.LocalTime;

public class BusinessDay {

    private final LocalTime startTime;
    private final LocalTime breakStartTime;
    private final LocalTime breakEndTime;
    private final LocalTime endTime;

    public boolean contains(LocalTime time) {
        return startTime.compareTo(time) * endTime.compareTo(time) <= 0;
    }

    public Duration duration() {
        return Duration.between(startTime, endTime);
    }

    public BusinessDay(int startHour, int startMinute, int endHour, int endMinute) {
        this.startTime = LocalTime.of(startHour, startMinute);
        this.endTime = LocalTime.of(endHour, endMinute);
        this.breakStartTime = null;
        this.breakEndTime = null;
    }

    public BusinessDay(LocalTime startTime, LocalTime endTime, LocalTime breakStartTime, LocalTime breakEndTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getBreakStartTime() {
        return breakStartTime;
    }

    public LocalTime getBreakEndTime() {
        return breakEndTime;
    }
}
