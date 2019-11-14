package com.innopolis.innometrics.restapi.exceptions;

public class ValidationException extends RuntimeException {
    ///Source: https://dev.to/cuongld2/create-apis-with-jwt-authorization-using-spring-boot-24f9

    private static final long serialVersionUID = 1L;
    private String msg;

    public ValidationException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
