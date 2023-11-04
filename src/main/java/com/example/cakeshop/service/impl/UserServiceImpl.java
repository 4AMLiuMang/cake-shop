package com.example.cakeshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cakeshop.entity.User;
import com.example.cakeshop.mapper.UserMapper;
import com.example.cakeshop.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
