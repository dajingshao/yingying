package com.joel.practice.common.response;

public class RawResponse extends DontWrapResponse<String>{
    public RawResponse(String body) {
        super(body);
    }
}
