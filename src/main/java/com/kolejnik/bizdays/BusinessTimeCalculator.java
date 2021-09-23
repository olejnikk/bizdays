package com.kolejnik.bizdays;

import java.time.Duration;
import java.time.LocalDateTime;

public interface BusinessTimeCalculator {

    boolean isBusinessTime(LocalDateTime dateTime);

    LocalDateTime plus(LocalDateTime dateTime, int hoursToAdd);

    LocalDateTime minus(LocalDateTime dateTime, int hoursToSubtract);

    LocalDateTime plus(LocalDateTime dateTime, Duration duration);

    LocalDateTime minus(LocalDateTime dateTime, Duration duration);

    LocalDateTime businessDayStartAfter(LocalDateTime dateTime);

    LocalDateTime businessDayEndAfter(LocalDateTime dateTime);

    LocalDateTime businessDayStartBefore(LocalDateTime dateTime);

    LocalDateTime businessDayEndBefore(LocalDateTime dateTime);

}
