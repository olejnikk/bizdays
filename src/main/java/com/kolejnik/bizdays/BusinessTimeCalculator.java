package com.kolejnik.bizdays;

import java.time.Duration;
import java.time.LocalDateTime;

public interface BusinessTimeCalculator {

    public boolean isBusinessTime(LocalDateTime dateTime);

    public LocalDateTime plus(LocalDateTime dateTime, int hoursToAdd);

    public LocalDateTime minus(LocalDateTime dateTime, int hoursToSubtract);

    public LocalDateTime plus(LocalDateTime dateTime, Duration duration);

    public LocalDateTime minus(LocalDateTime dateTime, Duration duration);

    public LocalDateTime businessDayStartAfter(LocalDateTime dateTime);

    public LocalDateTime businessDayEndAfter(LocalDateTime dateTime);

    public LocalDateTime businessDayStartBefore(LocalDateTime dateTime);

    public LocalDateTime businessDayEndBefore(LocalDateTime dateTime);
}
