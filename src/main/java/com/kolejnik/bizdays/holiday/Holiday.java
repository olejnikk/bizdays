package com.kolejnik.bizdays.holiday;

import java.time.LocalDate;

public abstract class Holiday {

    public abstract boolean isHoliday(LocalDate date);

    public abstract LocalDate nextAfter(LocalDate date);

    public LocalDate next() {
        return nextAfter(LocalDate.now());
    }
}
