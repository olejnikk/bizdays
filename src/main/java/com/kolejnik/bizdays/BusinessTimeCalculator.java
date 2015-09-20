package com.kolejnik.bizdays;

import java.time.LocalDateTime;

public interface BusinessTimeCalculator {

    public boolean isBusinessTime(LocalDateTime dateTime);

    public LocalDateTime plus(LocalDateTime dateTime, int hoursToAdd);

    public LocalDateTime minus(LocalDateTime dateTime, int hoursToSubtract);

    public LocalDateTime businessDayStartAfter(LocalDateTime dateTime);

    public LocalDateTime businessDayEndAfter(LocalDateTime dateTime);
}
