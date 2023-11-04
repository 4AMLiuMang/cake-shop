package com.example.cakeshop.dto;

import com.example.cakeshop.entity.Cake;
import lombok.Data;

@Data
public class CakeDto extends Cake {

    //订单数
    private Integer cakeTotal;
    //蛋糕评分
    private Double cakeRating;
}
