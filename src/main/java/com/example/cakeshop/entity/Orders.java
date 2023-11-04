package com.example.cakeshop.entity;

//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单表
 */
@Data
public class Orders implements Serializable {

    private Long id;

    //蛋糕ID
    private Long cakeId;

    //蛋糕名字
    private String cakeName;

    //蛋糕图片
    private String cakeImg;

    //蛋糕价格
    private Double cakePrice;

    //用户ID
    private Long userId;

    //用户昵称
    private String userNickname;

    //用户头像
    private String userAvatar;

    //用户手机
    private String userPhone;

    //用户地址
    private String userAddress;

    //订单创建时间，插入时填充
    private LocalDateTime createTime;

    //店铺出餐状态
    private Integer smStatus;

    //店铺出餐时间
    private LocalDateTime smTime;

    //骑手送餐状态
    private Integer deStatus;

    //骑手送餐时间
    private LocalDateTime deTime;

    //订单完成状态 0进行中 1顾客取消 2商家取消 3已完成
    private Integer ocStatus;

    //订单完成时间
    private LocalDateTime ocTime;

    //蛋糕评分
    private Double cakeRating;
}
