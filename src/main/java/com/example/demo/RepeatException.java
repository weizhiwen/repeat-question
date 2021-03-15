package com.example.demo;

/**
 * @author shixin
 * @date 2021/3/13
 */
public class RepeatException extends RuntimeException {
    public RepeatException() {
        super();
    }

    public RepeatException(String message) {
        super(message);
    }

    public RepeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatException(Throwable cause) {
        super(cause);
    }

    protected RepeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
