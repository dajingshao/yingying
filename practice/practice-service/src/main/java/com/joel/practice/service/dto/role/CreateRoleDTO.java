package com.joel.practice.service.dto.role;

import lombok.Data;

import java.util.List;
@Data
public class CreateRoleDTO {
    private String name;
    private String description;
    private List<String> authorities;
}
