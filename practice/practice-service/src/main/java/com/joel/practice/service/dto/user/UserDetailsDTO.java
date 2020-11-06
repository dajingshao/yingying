package com.joel.practice.service.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserDetailsDTO {
    private Long id;
    private String username;
    private Boolean enabled;
    private List<String> authorities;
}
