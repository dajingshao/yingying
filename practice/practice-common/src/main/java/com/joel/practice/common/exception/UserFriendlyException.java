package com.joel.practice.common.exception;

public class UserFriendlyException extends RuntimeException{
    private int code;

    public UserFriendlyException(String message) {
        super(message);
    }

    public UserFriendlyException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
