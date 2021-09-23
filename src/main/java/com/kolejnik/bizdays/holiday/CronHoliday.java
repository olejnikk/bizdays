package com.kolejnik.bizdays.holiday;

import com.kolejnik.bizdays.InvalidHolidayException;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CronHoliday implements Holiday {

    public static final Holiday SATURDAY = new CronHoliday("* * * ? * SAT *", "Saturday");
    public static final Holiday SUNDAY = new CronHoliday("* * * ? * SUN *", "Sunday");

    private final CronExpression cronExpression;
    private final String name;

    public CronHoliday(CronExpression cronExpression) {
        this(cronExpression, null);
    }

    public CronHoliday(String cronExpression) {
        this(cronExpression, null);
    }

    public CronHoliday(String cronExpression, String name) {
        try {
            CronExpression expression = new CronExpression(cronExpression);
            this.name = name;
            this.cronExpression = expression;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Expression could not be parsed", e);
        }
    }

    public CronHoliday(CronExpression cronExpression, String name) {
        this.name = name;
        this.cronExpression = cronExpression;
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        Date tmpDate = toDate(date);
        return cronExpression.isSatisfiedBy(tmpDate);
    }

    @Override
    public LocalDate nextAfter(LocalDate date) {
        Date tmpDate = toDate(date);
        Date nextDate = cronExpression.getNextValidTimeAfter(tmpDate);
        if (nextDate == null) {
            StringBuilder message = new StringBuilder("Cant find next holiday");
            if (name != null) {
                message.append(" of ").append(name);
            }
            throw new InvalidHolidayException(message.toString());
        }
        return toLocalDate(nextDate);
    }

    @Override
    public String toString() {
        if (name != null) {
            return name + " (" + cronExpression + ")";
        }
        return cronExpression.toString();
    }

    private static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private static LocalDate toLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
