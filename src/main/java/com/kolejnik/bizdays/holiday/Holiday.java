package com.kolejnik.bizdays.holiday;

import java.time.LocalDate;

public interface Holiday {

    boolean isHoliday(LocalDate date);

    LocalDate nextAfter(LocalDate date);

    default LocalDate next() {
        return nextAfter(LocalDate.now());
    }

}
