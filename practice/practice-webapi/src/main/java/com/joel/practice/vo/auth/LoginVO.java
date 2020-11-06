package com.joel.practice.vo.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
public class LoginVO {
    @NotBlank(message = "请输入登录账号")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$", message = "登录账号格式错误(由中英文数字组成、不含符号)")
    private String username;

    @NotBlank(message = "请输入登录密码")
    @Pattern(regexp = "^[A-Za-z0-9]{6,}$", message = "登录密码格式错误(由数字或者英文组成、6位以上、不含符号)")
    private String password;
}
