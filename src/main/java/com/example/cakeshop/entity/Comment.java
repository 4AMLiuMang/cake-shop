package com.example.cakeshop.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评论表
 */
@Data
public class Comment implements Serializable {

    private Long id;

    //蛋糕ID
    private Long cakeId;

    //蛋糕名字
    private String cakeName;

    //蛋糕图片
    private String cakeImg;

    //用户ID
    private Long userId;

    //用户昵称
    private String userNickname;

    //用户头像
    private String userAvatar;

    //评论内容
    private String cmtContent;

    //评论时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //插入和更新时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
