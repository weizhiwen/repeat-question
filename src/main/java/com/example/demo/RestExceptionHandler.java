package com.example.demo;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * @author shixin
 * @date 2021/3/13
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = RepeatException.class)
    public String bizExceptionHandler(RepeatException e) {
        log.error(e.getLocalizedMessage());
        return e.getLocalizedMessage();
    }

    @ExceptionHandler(value = Exception.class)
    public String bizExceptionHandler(Exception e) {
        log.error(e.getLocalizedMessage());
        return e.getLocalizedMessage();
    }
}
