package com.joel.practice.service.api;

import com.joel.practice.service.dto.role.CreateRoleDTO;

public interface RoleService {
    /**
     * 创建角色
     * @param createRoleDTO
     * @return
     */
    Long createRole(CreateRoleDTO createRoleDTO);
}
