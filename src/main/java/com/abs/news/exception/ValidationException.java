package com.abs.news.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ValidationException extends RuntimeException {

    private List<ValidationError> validationErrors = new ArrayList<>();

    public ValidationException() {
        super();

    }

    public ValidationException(ValidationError validationError) {
        super(validationError.getDefaultMessage());
        validationErrors.add(validationError);
    }

    public void addValidationError(ValidationError validationError) {
        validationErrors.add(validationError);
    }

    @Override
    public String getMessage() {

        return validationErrors.stream().map(ValidationError::getDefaultMessage).collect(Collectors.joining());
    }
}
