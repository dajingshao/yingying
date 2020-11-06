package com.joel.practice.service.api.impl;

import com.joel.practice.common.exception.UserFriendlyException;
import com.joel.practice.dao.entity.Authority;
import com.joel.practice.dao.entity.Role;
import com.joel.practice.dao.mapper.AuthorityMapper;
import com.joel.practice.dao.mapper.RoleMapper;
import com.joel.practice.service.api.RoleService;
import com.joel.practice.service.dto.role.CreateRoleDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private RoleMapper roleMapper;
    private AuthorityMapper authorityMapper;

    public RoleServiceImpl(RoleMapper roleMapper, AuthorityMapper authorityMapper) {
        this.roleMapper = roleMapper;
        this.authorityMapper = authorityMapper;
    }

    @Override
    public Long createRole(CreateRoleDTO createRoleDTO) {
        Role role = new Role();
        role.setName(createRoleDTO.getName());
        role.setDescription(createRoleDTO.getDescription());
        if(roleMapper.insert(role) == 0){
            throw new UserFriendlyException("创建角色失败");
        }

        for (String authorityName :createRoleDTO.getAuthorities()) {
            Authority authority = new Authority();
            authority.setRoleId(role.getId());
            authority.setName(authorityName);
            if(authorityMapper.insert(authority) == 0){
                throw new UserFriendlyException("添加关联权限失败");
            }
        }

        return role.getId();
    }
}
