package com.joel.practice.common.response;

import java.io.Serializable;

public class AjaxResponse<T> implements Serializable {
    private Boolean success;
    private Boolean unAuthorizedRequest;
    private ErrorInfo error;
    private T result;

    public AjaxResponse() {
    }

    public AjaxResponse(T data) {
        success = true;
        unAuthorizedRequest = false;
        result = data;
    }

    public AjaxResponse(int code, String message) {
        success = false;
        unAuthorizedRequest = false;
        error = new ErrorInfo(code, message);
    }

    public AjaxResponse(Exception e) {
        this(0, e.getMessage());
    }

    public Boolean isSuccess() {
        return success;
    }

    public Boolean isUnAuthorizedRequest() {
        return unAuthorizedRequest;
    }

    public ErrorInfo getError() {
        return error;
    }

    public T getResult() {
        return result;
    }

    public static <T> AjaxResponse<T> ok(T data) {
        return new AjaxResponse<T>(data);
    }

    public static <T> AjaxResponse<T> err(Exception e) {
        return new AjaxResponse<T>(e);
    }

    public static <T> AjaxResponse<T> err(String message) {
        return new AjaxResponse<T>(0, message);
    }

    public static <T> AjaxResponse<T> err(int code, String message) {
        return new AjaxResponse<T>(code, message);
    }
}
