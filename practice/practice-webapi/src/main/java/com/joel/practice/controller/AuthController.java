package com.joel.practice.controller;

import com.joel.practice.service.api.AuthService;
import com.joel.practice.service.dto.auth.Token;
import com.joel.practice.vo.auth.LoginVO;
import com.joel.practice.vo.auth.RefreshTokenVO;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableWebSecurity
@RequestMapping("/admin/auth")
public class AuthController extends ControllerBase{
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginVO model, BindingResult result) {
        checkError(result);
        return authService.getToken(model.getUsername(), model.getPassword());
    }

    @PostMapping("/refreshToken")
    public Token refreshToken(@RequestBody RefreshTokenVO model, BindingResult result) {
        checkError(result);
        return authService.refreshToken(model.getToken());
    }
}
