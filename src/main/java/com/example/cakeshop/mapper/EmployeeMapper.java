package com.example.cakeshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cakeshop.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
