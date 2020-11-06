package com.joel.practice.service.api;

import com.joel.practice.service.dto.auth.Token;

public interface AuthService {
    /**
     * 获取token
     * @param username
     * @param password
     * @return
     */
    Token getToken(String username, String password);

    /**
     * 刷新token
     * @param token
     * @return
     */
    Token refreshToken(String token);
}
