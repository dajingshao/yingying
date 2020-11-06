package com.joel.practice.service.dto.auth;

import com.joel.practice.service.security.JwtTokenProvider;
import lombok.Data;

@Data
public class Token {
    private String accessToken;
    private String refreshToken;
    private String tokenType = JwtTokenProvider.TOKEN_TYPE;
    private Integer expiresIn = JwtTokenProvider.REFRESH_TOKEN_EXPIRATION_SECONDS;
}
