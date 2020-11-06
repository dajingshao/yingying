package com.joel.practice.controller;

import com.joel.practice.service.dto.user.UserDetailsDTO;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableWebSecurity
@RequestMapping("/admin/user")
public class UserController extends ControllerBase {

    /**
     * 获取当前已登录用户基本信息
     * @return
     */
    @GetMapping("/getCurrentUserInfo")
    public UserDetailsDTO getCurrentUserInfo() {
        return getSession();
    }

}
