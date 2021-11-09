package com.jeisson.server.model;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class Response {
    protected LocalDateTime timeStamp;
    protected int statusCode;
    protected HttpStatus httpStatus;
    protected String reason;
    protected String message;
}
