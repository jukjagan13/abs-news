package com.abs.news.exception;

import lombok.Data;

@Data
public class ValidationError {
    public ValidationError() {

    }

    public ValidationError(String objectName, String defaultMessage) {
        this.objectName = objectName;
        this.defaultMessage = defaultMessage;
    }

    public ValidationError(String code, String objectName, String defaultMessage) {
        this.objectName = objectName;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    private String defaultMessage;
    private String objectName;
    private String field;
    private String rejectedValue;
    private String code;
}