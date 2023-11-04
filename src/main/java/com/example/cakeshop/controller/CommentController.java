package com.example.cakeshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cakeshop.common.R;
import com.example.cakeshop.entity.Comment;
import com.example.cakeshop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 新增评论
     * @param comment
     * @return
     */
    @PostMapping("/addComment")
    public R<String> save(@RequestBody Comment comment){//@RequestBody将前端的json数据封装
        //不为空则执行新增
        if(comment != null){
            commentService.save(comment);
            return R.success("发表评论成功");
        }
        return R.error("未知错误");
    }

    /**
     * 评论信息分页模糊查询
     * @param page
     * @param pageSize
     * @param cmtContent
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String cakeName, String userNickname, String cmtContent){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //添加搜索条件//模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(cakeName),Comment::getCakeName,cakeName);
        queryWrapper.like(StringUtils.isNotEmpty(userNickname),Comment::getUserNickname,userNickname);
        queryWrapper.like(StringUtils.isNotEmpty(cmtContent),Comment::getCmtContent,cmtContent);
        //添加排序条件//时间排序
        queryWrapper.orderByDesc(Comment::getCreateTime);
        //执行查询
        commentService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 用户！！！
     * 根据用户id查询评论信息
     * 对于更新操作，这里只调用了查询，用于更新时的数据回显，更新操作在update方法中复用即可完成
     * @param id
     * @return
     */
    @GetMapping("/getByUid")
    public R<Page> getByUid(Long id, int page, int pageSize){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getUserId,id);
        //添加排序条件//时间排序
        queryWrapper.orderByDesc(Comment::getCreateTime);
        //执行查询操作
        commentService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 蛋糕！！！
     * 根据蛋糕id查询评论信息
     * @param id
     * @return
     */
    @GetMapping("/getByCid")
    public R<Page> getByCid(Long id, int page, int pageSize){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getCakeId,id);
        //添加排序条件//时间排序
        queryWrapper.orderByDesc(Comment::getCreateTime);
        //执行查询操作
        commentService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id批量删除评论
     * @param ids
     * @return
     */
    @DeleteMapping("deleteCmt")
    public R delete(String ids){
        String[] list = ids.split(",");
        for (String id : list) {
            commentService.removeById(Long.parseLong(id));
        }
        return R.success("删除成功");
    }
}
