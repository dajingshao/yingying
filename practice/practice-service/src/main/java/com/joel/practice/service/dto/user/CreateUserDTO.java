package com.joel.practice.service.dto.user;

import lombok.Data;

@Data
public class CreateUserDTO {
  private String username;
  private String password;
  private Boolean enabled;
}
