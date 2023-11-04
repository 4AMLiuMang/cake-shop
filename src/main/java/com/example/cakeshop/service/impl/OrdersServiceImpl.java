package com.example.cakeshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cakeshop.entity.Orders;
import com.example.cakeshop.mapper.OrdersMapper;
import com.example.cakeshop.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
}
