BizDays [![Build Status](https://travis-ci.org/olejnikk/bizdays.svg?branch=master)](https://travis-ci.org/olejnikk/bizdays)
=======
Java date and time calculator for operations on business days and working time such as:

* check if a date is a business day or a holiday,
* get next business day,
* get business day after (before) a given date,
* count business days between given dates,
* add (subtract) business days to (from) a given date,
* check if given time is working time,
* next EOD, next EOD after (before) given time,
* add (subtract) working hours to given time.

## Project structure
    ...
    ├── calendar                         # business days
    │   ├── BusinessCalendar
    │   └── ...                          # place for national business calendar implementations
    ├── holiday                          # holiday definitions
    │   ├── CronHoliday
    │   ├── GregorianEasterBasedHoliday
    │   ├── FixedYearlyHoliday
    │   ├── Holiday
    │   ├── MovableYearlyHoliday
    │   └── YearlyHoliday
    ├── schedule                         # work time
    │   ├── BusinessDay                  # start/end of work time
    │   └── BusinessSchedule             # BusinessTimeCalculator implementation
    ├── BusinessDayCalculator            # business days methods
    └── BusinessTimeCalculator           # business time methods

## Examples

#### Business days operations
```java
BusinessCalendar calendar = AmericanBusinessCalendar.getInstance();

LocalDate nbd = calendar.nextBusinessDay();
LocalDate next5Days = calendar.plus(LocalDate.now(), 5);
int workingDays2016 = calendar.businessDaysBetween(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31));
```

#### Work time operations
```java
BusinessSchedule schedule = new BusinessSchedule(calendar);
        
boolean workNow = schedule.isBusinessTime(LocalDateTime.now());
LocalDateTime next16h = schedule.plus(LocalDateTime.now(), 16);
LocalDateTime next15min = schedule.plus(LocalDateTime.now(), Duration.ofMinutes(15));
```

#### Custom business calendar
```java
BusinessCalendar calendar = new BusinessCalendar();
calendar.addHoliday(CronHoliday.SATURDAY);
calendar.addHoliday(CronHoliday.SUNDAY);
calendar.addHoliday(FixedYearlyHoliday.NEW_YEAR);
calendar.addHoliday(FixedYearlyHoliday.CHRISTMAS);
calendar.addHoliday(EasterBasedHoliday.EASTER);
calendar.addHoliday(EasterBasedHoliday.EASTER_MONDAY);

// add Valentine's Day as a holiday
calendar.addHoliday(new FixedYearlyHoliday(Month.FEBRUARY, 14));
// as well as every first thursday in a month
calendar.addHoliday(new CronHoliday("* * * ? * THU#1 *"));
```

#### Custom business schedule
Let's assume that your company works from 7am to 3pm except fridays, when it starts at 9am and closes at 2pm.
```java
BusinessSchedule schedule = new BusinessSchedule(calendar);
schedule.setDefaultBusinessDay(new BusinessDay(LocalTime.of(7, 0), LocalTime.of(15, 0)));
schedule.putBusinessDay(DayOfWeek.FRIDAY, new BusinessDay(9, 0, 14, 0));
```
