package com.abs.news.exception;

import lombok.Data;
import org.slf4j.MDC;

import java.sql.Timestamp;

@Data
public class ErrorResponse {

    private String correlationId = MDC.get("correlationId");
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private int status;
    private String message;
    private String code;

    public ErrorResponse(int status, String message, String code){
        this.status = status;
        this.message = message;
        this.code = code;
    }
}