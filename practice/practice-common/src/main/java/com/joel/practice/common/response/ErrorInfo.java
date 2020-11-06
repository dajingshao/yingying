package com.joel.practice.common.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorInfo implements Serializable {
    private Integer code;
    private String message;

    public ErrorInfo() { }

    public ErrorInfo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorInfo(String message) {
        this(0,message);
    }

    public ErrorInfo(Exception e){
        this(0,e.getMessage());
    }
}
