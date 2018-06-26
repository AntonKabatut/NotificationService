package com.anton.kursach.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GeneralExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(Exception ex, HttpServletRequest request) {
        log.error("URI={}, {}", request.getRequestURI(), ex);
        return ErrorResponse.of(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleException(EntityNotFoundException ex, HttpServletRequest request) {
        log.error("URI={}, {}", request.getRequestURI(), ex);
        return ErrorResponse.of(ex.getMessage(), HttpStatus.NOT_FOUND.value(), request.getRequestURI());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(BadRequestException ex, HttpServletRequest request) {
        log.error("URI={}, {}", request.getRequestURI(), ex);
        return ErrorResponse.of(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
    }

}
