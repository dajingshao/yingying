package com.joel.practice.service.api;

import com.joel.practice.service.dto.user.CreateUserDTO;
import com.joel.practice.service.dto.user.UserDetailsDTO;

public interface UserService {
    /**
     * 根据userId获取用户详情
     * @param id
     * @return
     */
    UserDetailsDTO getUserById(Long id);

    /**
     * 创建用户
     * @param createUserDTO
     * @return
     */
    Long createUser(CreateUserDTO createUserDTO);
}
