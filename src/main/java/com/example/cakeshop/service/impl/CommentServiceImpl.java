package com.example.cakeshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cakeshop.entity.Comment;
import com.example.cakeshop.mapper.CommentMapper;
import com.example.cakeshop.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
