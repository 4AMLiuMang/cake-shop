package com.example.cakeshop.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工表
 */
@Data
public class Employee implements Serializable {

    private Long id;

    //账号
    private String empUsername;

    //密码
    private String empPassword;

    //昵称
    private String empNickname;

    //头像
    private String empAvatar;

    //性别 0 女 1 男
    private Integer empGender;

    //手机
    private String empPhone;

    //身份证
    private String empIdentity;

    //邮箱
    private String empEmail;

    //地址
    private String empAddress;

    //禁用状态 0禁用 1启用
    private Integer empStatus;

    //插入时填充字段
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //插入和更新时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
