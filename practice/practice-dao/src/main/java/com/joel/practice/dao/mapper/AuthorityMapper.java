package com.joel.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joel.practice.dao.entity.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AuthorityMapper extends BaseMapper<Authority> {
    /**
     * 根据userId获取权限
     * @param userId
     * @return
     */
    List<Authority> selectByUserId(Long userId);
}
