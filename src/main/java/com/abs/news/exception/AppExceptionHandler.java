package com.abs.news.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ErrorResponse validationExceptionHandler(ValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.EXPECTATION_FAILED.value(), ex.getValidationErrors().get(0).getDefaultMessage(), ex.getValidationErrors().get(0).getCode());
        log.error("Validation Error", ex);
        return errorResponse;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ErrorResponse validationExceptionHandler(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.EXPECTATION_FAILED.value(), "Invalid Input", "Invalid Input");
        log.error("Validation Error", ex);
        return errorResponse;
    }

    @ExceptionHandler(value = {DataAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse dataAccessExceptionHandler(DataAccessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Database Error", "");
        log.error("DataAccessException", ex);
        return errorResponse;
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse uncaughtExceptionHandler(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "");
        log.error("Server Error", ex);
        return errorResponse;
    }
}
