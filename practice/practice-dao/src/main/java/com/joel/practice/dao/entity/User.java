package com.joel.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("enabled")
    private Boolean enabled = true;

    @TableLogic
    @TableField("deleted")
    private Boolean deleted = false;

    @TableField("creation_time")
    private Date creationTime = new Date();
}
