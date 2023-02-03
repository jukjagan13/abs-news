package com.abs.news.exception;

import lombok.Data;

@Data
public class SystemException extends RuntimeException {

    public SystemException(String message, Throwable t){
        super(message,t);
    }

    public SystemException(String message){
        super(message);
    }
}
