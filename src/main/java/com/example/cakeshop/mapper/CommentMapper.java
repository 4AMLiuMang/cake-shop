package com.example.cakeshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cakeshop.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
