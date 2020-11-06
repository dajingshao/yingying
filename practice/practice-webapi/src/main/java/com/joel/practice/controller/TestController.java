package com.joel.practice.controller;

import com.joel.practice.service.api.RoleService;
import com.joel.practice.service.api.UserService;
import com.joel.practice.service.dto.role.CreateRoleDTO;
import com.joel.practice.service.dto.user.CreateUserDTO;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@EnableWebSecurity
@Transactional
public class TestController {

    private UserService userService;
    private RoleService roleService;

    public TestController(UserService userService,
                          RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("test")
    public String test(){

        return "ok";
    }
}
