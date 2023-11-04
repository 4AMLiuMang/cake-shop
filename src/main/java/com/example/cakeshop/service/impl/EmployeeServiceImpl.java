package com.example.cakeshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cakeshop.entity.Employee;
import com.example.cakeshop.mapper.EmployeeMapper;
import com.example.cakeshop.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
