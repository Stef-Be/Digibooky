package com.switchfully.duckbusters.digibooky.api;

import com.switchfully.duckbusters.digibooky.service.ValidationService;
import com.switchfully.duckbusters.digibooky.service.exceptions.UnauthorizatedException;
import com.switchfully.duckbusters.digibooky.service.exceptions.UnknownPersonException;
import com.switchfully.duckbusters.digibooky.service.exceptions.WrongPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class DigibookyExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(DigibookyExceptionHandler.class);
    @ExceptionHandler(IllegalArgumentException.class)
    protected void courseID(IllegalArgumentException ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        response.sendError(BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizatedException.class)
    protected void courseID(UnauthorizatedException ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        response.sendError(FORBIDDEN.value(), ex.getMessage());
    }
    @ExceptionHandler(UnknownPersonException.class)
    protected void courseID(UnknownPersonException ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        response.sendError(NOT_FOUND.value(), ex.getMessage());
    }
    @ExceptionHandler(WrongPasswordException.class)
    protected void courseID(WrongPasswordException ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        response.sendError(UNAUTHORIZED.value(), ex.getMessage());
    }


}
