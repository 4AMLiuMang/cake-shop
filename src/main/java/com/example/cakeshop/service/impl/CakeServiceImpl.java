package com.example.cakeshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cakeshop.entity.Cake;
import com.example.cakeshop.mapper.CakeMapper;
import com.example.cakeshop.service.CakeService;
import org.springframework.stereotype.Service;

@Service
public class CakeServiceImpl extends ServiceImpl<CakeMapper, Cake> implements CakeService {
}
