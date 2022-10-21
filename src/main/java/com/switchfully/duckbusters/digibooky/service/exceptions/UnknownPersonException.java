package com.switchfully.duckbusters.digibooky.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnknownPersonException extends RuntimeException {
    public UnknownPersonException() {
        super("The Person is not found!");
    }
}
