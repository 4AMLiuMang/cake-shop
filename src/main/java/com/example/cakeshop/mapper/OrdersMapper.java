package com.example.cakeshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cakeshop.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
