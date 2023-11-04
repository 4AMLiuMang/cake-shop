package com.example.cakeshop.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 蛋糕表
 */
@Data
public class Cake implements Serializable {

    private Long id;

    //蛋糕名字
    private String cakeName;

    //蛋糕图片
    private String cakeImg;

    //蛋糕类型
    private String cakeType;

    //蛋糕介绍
    private String cakeIntro;

    //蛋糕价格
    private Double cakePrice;

    //蛋糕是否推荐 0不推荐 1推荐
    private Integer cakeRec;

    //蛋糕是否售罄 0售罄 1未售罄
    private Integer cakeStatus;

    //插入时填充字段
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //插入和更新时填充字段
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
