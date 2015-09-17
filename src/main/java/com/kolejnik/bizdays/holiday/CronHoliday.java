package com.kolejnik.bizdays.holiday;

import com.kolejnik.bizdays.DateUtils;
import com.kolejnik.bizdays.InvalidHolidayException;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

public class CronHoliday extends Holiday {

    CronExpression cronExpression;

    private String name;

    public static Holiday SATURDAY = new CronHoliday("* * * ? * SAT *");
    public static Holiday SUNDAY = new CronHoliday("* * * ? * SUN *");

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
            throw new Error(e);
        }
    }

    public CronHoliday(CronExpression cronExpression, String name) {
        this.name = name;
        this.cronExpression = cronExpression;
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        Date tmpDate = DateUtils.toDate(date);
        return cronExpression.isSatisfiedBy(tmpDate);
    }

    @Override
    public LocalDate nextAfter(LocalDate date) {
        Date tmpDate = DateUtils.toDate(date);
        Date nextDate = cronExpression.getNextValidTimeAfter(tmpDate);
        if (nextDate == null) {
            StringBuilder message = new StringBuilder("Cant find next holiday");
            if (name != null) {
                message.append(" of ").append(name);
            }
            throw new InvalidHolidayException(message.toString());
        }
        return DateUtils.toLocalDate(nextDate);
    }

    public CronExpression getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(CronExpression cronExpression) {
        this.cronExpression = cronExpression;
    }

    public void setCronExpression(String expression) throws ParseException {
        this.cronExpression = new CronExpression(expression);
    }
}
