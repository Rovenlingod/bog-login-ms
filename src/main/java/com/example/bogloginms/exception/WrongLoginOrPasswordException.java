package com.example.bogloginms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongLoginOrPasswordException extends RuntimeException {

    public WrongLoginOrPasswordException() {
    }

    public WrongLoginOrPasswordException(String message) {
        super(message);
    }

    public WrongLoginOrPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
