package com.kolejnik.bizdays;

public class InvalidHolidayException extends RuntimeException {
    public InvalidHolidayException() {
        super();
    }

    public InvalidHolidayException(String message) {
        super(message);
    }

    public InvalidHolidayException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHolidayException(Throwable cause) {
        super(cause);
    }

    protected InvalidHolidayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
