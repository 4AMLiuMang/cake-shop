package com.example.cakeshop.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 */
@Data
public class User implements Serializable {

    private Long id;

    //账号
    private String userUsername;

    //密码
    private String userPassword;

    //昵称
    private String userNickname;

    //头像
    private String userAvatar;

    //性别 0 女 1 男
    private Integer userGender;

    //手机
    private String userPhone;

    //邮箱
    private String userEmail;

    //地址
    private String userAddress;

    //禁用状态 0禁用 1启用
    private Integer userStatus;

    //插入时填充字段
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //插入和更新时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
