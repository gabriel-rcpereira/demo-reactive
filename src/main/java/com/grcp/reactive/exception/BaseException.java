package com.grcp.reactive.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends Exception {

    private HttpStatus errorStatus;

    public BaseException(String message, HttpStatus errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }
}
