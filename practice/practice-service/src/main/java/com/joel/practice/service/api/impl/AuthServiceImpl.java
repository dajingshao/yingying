package com.joel.practice.service.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.joel.practice.common.exception.UserFriendlyException;
import com.joel.practice.dao.entity.User;
import com.joel.practice.dao.mapper.UserMapper;
import com.joel.practice.service.api.AuthService;
import com.joel.practice.service.dto.auth.Token;
import com.joel.practice.service.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtTokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    public AuthServiceImpl(JwtTokenProvider tokenProvider,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public Token getToken(String username, String password) {
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, username)
        );
        if(user == null){
            throw new UserFriendlyException("用户名错误");
        }

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new UserFriendlyException("用户名或者密码错误");
        }

        if(!user.getEnabled()){
            throw new UserFriendlyException("账号禁止登录");
        }

        String sub = String.valueOf(user.getId());
        Token token = new Token();
        token.setAccessToken(tokenProvider.generateAccessToken(sub));
        token.setRefreshToken(tokenProvider.generateRefreshToken(sub));
        return token;
    }

    @Override
    public Token refreshToken(String token) {
        if(!tokenProvider.validateToken(token)){
            throw new UserFriendlyException("无效token");
        }

        Claims claims = tokenProvider.getClaimsFromToken(token);
        String sub = claims.getSubject();
        if(StringUtils.isEmpty(sub)){
            throw new UserFriendlyException("无效Subject");
        }

        Token resultToken = new Token();
        resultToken.setAccessToken(tokenProvider.generateAccessToken(sub));
        resultToken.setRefreshToken(tokenProvider.generateRefreshToken(sub));
        return resultToken;
    }
}
