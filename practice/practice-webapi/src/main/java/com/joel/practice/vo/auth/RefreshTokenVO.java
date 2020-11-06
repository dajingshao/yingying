package com.joel.practice.vo.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenVO {
    @NotBlank(message = "缺少token")
    private String token;
}
