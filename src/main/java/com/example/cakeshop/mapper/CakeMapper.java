package com.example.cakeshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cakeshop.entity.Cake;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CakeMapper extends BaseMapper<Cake> {
}
