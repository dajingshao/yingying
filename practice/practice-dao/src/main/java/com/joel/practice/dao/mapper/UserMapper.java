package com.joel.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joel.practice.dao.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
