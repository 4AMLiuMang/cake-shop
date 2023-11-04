package com.example.cakeshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cakeshop.common.R;
import com.example.cakeshop.entity.Comment;
import com.example.cakeshop.entity.User;
import com.example.cakeshop.service.CommentService;
import com.example.cakeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public R<String> save(@RequestBody User user){//@RequestBody将前端的json数据封装
        //将密码进行md5加密处理
        String password = user.getUserPassword();
        user.setUserPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        //保存没有的内容
        user.setUserNickname("新用户");
        if (user.getUserAvatar() == null){
            user.setUserAvatar("https://picture-storage-zsm.oss-cn-hangzhou.aliyuncs.com/2023/01/30/2c42142883d541c38ad0e7b13e31dc4efile.png");
        }
        user.setUserGender(1);
        user.setUserPhone("18888888888");
        user.setUserAddress("暂无数据");
        user.setUserStatus(1);
        //2.根据页面提交的用户名查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserUsername,user.getUserUsername());
        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(User::getUserEmail,user.getUserEmail());
        User us = userService.getOne(queryWrapper);
        User el = userService.getOne(queryWrapper2);
        //3.查询不到则执行新增
        if(us == null && el == null){
            userService.save(user);
            return R.success("用户注册成功");
        } else if (us != null) {
            return R.error("此账号已经存在");
        }
        return R.error("此邮箱已经存在");
    }

    /**
     * 用户登录
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user) {
        //1.将页面提交的密码进行md5加密处理
        String password = user.getUserPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2.根据页面提交的用户名查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserUsername,user.getUserUsername());
        User us = userService.getOne(queryWrapper);//执行登录。。。getOne,数据库设置唯一，就可以通过这个方法
        //3.查询不到则返回失败
        if(us == null){
            return R.error("用户不存在");
        }
        //4.密码不一致返回失败
        if (!us.getUserPassword().equals(password)){
            return R.error("密码错误");
        }
        //5.账号被禁用返回失败
        Integer status = us.getUserStatus();
        if(status != 1){
            return R.error("该用户已被禁用，请联系管理员处理");
        }
        //6.登录成功，将id存入session并返回登录结果
        request.getSession().setAttribute("user",us.getId());
        return R.success(us);
    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的员工的id
        request.getSession().removeAttribute("user");
        return R.success("退出成功");
    }

    /**
     * 用户信息分页模糊查询
     * @param page
     * @param pageSize
     * @param userUsername
     * @param userNickname
     * @param userEmail
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String userUsername, String userNickname, String userEmail){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //添加搜索条件//模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(userUsername),User::getUserUsername,userUsername);
        queryWrapper.like(StringUtils.isNotEmpty(userNickname),User::getUserNickname,userNickname);
        queryWrapper.like(StringUtils.isNotEmpty(userEmail),User::getUserEmail,userEmail);
        //添加排序条件//时间排序
        queryWrapper.orderByDesc(User::getUpdateTime);
        //执行查询
        userService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id修改密码
     * @param
     * @return
     */
    @PutMapping("/editPwd")
    public R<String> editPwd(@RequestBody Map<String,String> map){//@RequestBody将前端的json数据封装
        User user = userService.getById(map.get("id"));
        //前台传来的旧密码
        String oldPassword = DigestUtils.md5DigestAsHex(map.get("oldPassword").getBytes());
        //前台传来的新密码
        String newPassword = DigestUtils.md5DigestAsHex(map.get("userPassword").getBytes());
        if (!oldPassword.equals(user.getUserPassword())){
            return R.error("旧密码输入错误");
        }
        if (!Objects.equals(user.getUserPassword(), newPassword)) {
            user.setUserPassword(newPassword);
            userService.updateById(user);
            return R.success("密码修改成功");
        }
        return R.error("新密码和旧密码相同");
    }

    /**
     * 根据id修改用户信息
     * @param user
     * @return
     */
    @PutMapping("update")
    public R<String> update(@RequestBody User user){//@RequestBody将前端的json数据封装HttpServletRequest request取当前登录用户
        //根据页面提交的用户名查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserUsername,user.getUserUsername());
        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(User::getUserEmail,user.getUserEmail());
        User us = userService.getOne(queryWrapper);
        User el = userService.getOne(queryWrapper2);
        if (us != null && !us.getId().equals(user.getId())) {
            return R.error("此用户名已存在");
        }
        if (el != null && !el.getId().equals(user.getId())) {
            return R.error("此邮箱已存在");
        }
        //执行更新
        userService.updateById(user);
        return R.success("用户信息修改成功");
    }

    /**
     * 根据id查询用户信息
     * 对于更新操作，这里只调用了查询，用于更新时的数据回显，更新操作在update方法中复用即可完成
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<User> getById(@PathVariable Long id){//@PathVariable取请求路径里面的id
        //执行查询操作
        User user = userService.getById(id);
        if(user != null){
            return R.success(user);
        }
        return R.error("没有查询到对应用户信息");
    }

    /**
     * 更改用户禁用状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R changeStatus(@PathVariable int status,String ids){
        String[] idList = ids.split(",");
        for (String id : idList) {
            User user = new User();
            user.setId(Long.parseLong(id));
            user.setUserStatus(status);
            userService.updateById(user);
        }
        return R.success("更新状态成功");
    }

    /**
     * 根据id批量删除用户
     * 删除用户后，需要将评论中的信息一起修改
     * @param ids
     * @return
     */
    @Autowired
    private CommentService commentService;
    @DeleteMapping("deleteUser")
    public R delete(String ids){
        String[] list = ids.split(",");
        for (String id : list) {
            userService.removeById(Long.parseLong(id));
            //修改评论表中的信息
            LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Comment::getUserId, id);
            List<Comment> cmt = commentService.list(queryWrapper);
            for (int i = 0; i < cmt.size(); i++) {
                Comment comment = cmt.get(i);
                comment.setUserNickname("该用户已注销");
                comment.setUserAvatar("https://picture-storage-zsm.oss-cn-hangzhou.aliyuncs.com/2023/01/30/2c42142883d541c38ad0e7b13e31dc4efile.png");
                LambdaUpdateWrapper<Comment> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(Comment::getId, comment.getId());
                commentService.update(comment,updateWrapper);
            }
        }
        return R.success("删除成功");
    }
}
