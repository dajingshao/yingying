package com.joel.practice.service.api.impl;

import com.joel.practice.common.exception.UserFriendlyException;
import com.joel.practice.dao.entity.Authority;
import com.joel.practice.dao.entity.User;
import com.joel.practice.dao.mapper.AuthorityMapper;
import com.joel.practice.dao.mapper.UserMapper;
import com.joel.practice.service.api.UserService;
import com.joel.practice.service.dto.user.CreateUserDTO;
import com.joel.practice.service.dto.user.UserDetailsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private ModelMapper modelMapper;
    private AuthorityMapper authorityMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper,
                           ModelMapper modelMapper,
                           AuthorityMapper authorityMapper,
                           PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.modelMapper = modelMapper;
        this.authorityMapper = authorityMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetailsDTO getUserById(Long id) {
        User user = userMapper.selectById(id);
        if(user == null){
            throw new UserFriendlyException("无效用户id");
        }
        List<String> authorities = authorityMapper.selectByUserId(user.getId())
                .stream().map(Authority::getName).collect(Collectors.toList());
        UserDetailsDTO userDetailsDTO = modelMapper.map(user, UserDetailsDTO.class);
        userDetailsDTO.setAuthorities(authorities);
        return userDetailsDTO;
    }

    @Override
    public Long createUser(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));

        if(createUserDTO.getEnabled() != null){
            user.setEnabled(createUserDTO.getEnabled());
        }

        if(userMapper.insert(user) == 0){
            throw new UserFriendlyException("创建用户失败");
        }
        return user.getId();
    }
}
