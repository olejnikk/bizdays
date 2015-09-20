package com.kolejnik.bizdays.schedule;

import java.time.Duration;
import java.time.LocalTime;

public class BusinessDay {

    private LocalTime startTime;

    private LocalTime endTime;

    public boolean contains(LocalTime time) {
        return startTime.compareTo(time) * endTime.compareTo(time) <= 0;
    }

    public Duration duration() {
        return Duration.between(startTime, endTime);
    }

    public BusinessDay(int startHour, int startMinute, int endHour, int endMinute) {
        this.startTime = LocalTime.of(startHour, startMinute);
        this.endTime = LocalTime.of(endHour, endMinute);
    }

    public BusinessDay(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
