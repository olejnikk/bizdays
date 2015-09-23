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
    ├── calendar                     # business days
    │   ├── BusinessCalendar
    │   ├── BusinessCalendarFactory  # factory for national business calendars
    │   └── ...                      # place for national business calendar implementations
    ├── holiday                      # holiday definitions
    │   ├── CronHoliday
    │   ├── EasterBasedHoliday
    │   ├── FixedYearlyHoliday
    │   ├── Holiday
    │   ├── MovableYearlyHoliday
    │   └── YearlyHoliday
    ├── schedule                     # work time 
    │   ├── BusinessDay              # start/end of work time
    │   └── BusinessSchedule         # BusinessTimeCalculator implementation
    ├── BusinessDayCalculator        # business days methods
    └── BusinessTimeCalculator       # business time methods

