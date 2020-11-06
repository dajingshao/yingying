package com.joel.practice.common.response;

public class DontWrapResponse<T> {
    private T body;

    public DontWrapResponse(T body) {
        this.body = body;
    }

    public T getBody(){
        return body;
    }
}
