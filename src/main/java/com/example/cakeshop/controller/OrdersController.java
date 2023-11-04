package com.example.cakeshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cakeshop.common.R;
import com.example.cakeshop.entity.Comment;
import com.example.cakeshop.entity.Orders;
import com.example.cakeshop.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/addOrders")
    public R<String> save(@RequestBody Orders orders){//@RequestBody将前端的json数据封装
        //保存没有的内容
        //订单创建时间
        orders.setCreateTime(LocalDateTime.now());
        //店铺出餐状态
        orders.setSmStatus(0);
        //店铺出餐时间
        orders.setSmTime(LocalDateTime.now());
        //骑手送餐状态
        orders.setDeStatus(0);
        //骑手送餐时间
        orders.setDeTime(LocalDateTime.now());
        //订单完成状态 0进行中 1顾客取消 2商家取消 3已完成
        orders.setOcStatus(0);
        //订单完成时间
        orders.setOcTime(LocalDateTime.now());
        //蛋糕评分
        orders.setCakeRating(5.0);
        //执行添加
        ordersService.save(orders);
        return R.success("下单成功");
    }

    /**
     * 订单信息分页模糊查询
     * @param page
     * @param pageSize
     * @param cakeName
     * @param userNickname
     * @param userAddress
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String cakeName, String userNickname, String userAddress){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加搜索条件//模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(cakeName), Orders::getCakeName,cakeName);
        queryWrapper.like(StringUtils.isNotEmpty(userNickname), Orders::getUserNickname,userNickname);
        queryWrapper.like(StringUtils.isNotEmpty(userAddress), Orders::getUserAddress,userAddress);
        //添加排序条件//时间排序
        queryWrapper.orderByDesc(Orders::getCreateTime);
        //执行查询
        ordersService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id查询订单信息
     * 对于更新操作，这里只调用了查询，用于更新时的数据回显，更新操作在update方法中复用即可完成
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Orders> getById(@PathVariable Long id){//@PathVariable取请求路径里面的id
        //执行查询操作
        Orders orders = ordersService.getById(id);
        if(orders != null){
            return R.success(orders);
        }
        return R.error("没有查询到对应订单信息");
    }

    /**
     * 用户！！！
     * 根据用户id查询订单信息
     * 用于用户的订单查询
     * 对于更新操作，这里只调用了查询，用于更新时的数据回显，更新操作在update方法中复用即可完成
     * @param id
     * @return
     */
    @GetMapping("/getByUid")
    public R<Page> getByUid(Long id, int page, int pageSize){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId,id);
        //添加排序条件//时间排序
        queryWrapper.orderByDesc(Orders::getCreateTime);
        //执行查询操作
        ordersService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 蛋糕！！！
     * 根据蛋糕id查询订单
     * 用于计算蛋糕的评分
     * @param id
     * @return
     */
    @GetMapping("/getByCid")
    public R<Page> getByCid(Long id, int page, int pageSize){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getCakeId,id);
        //添加排序条件//时间排序
        queryWrapper.orderByDesc(Orders::getCreateTime);
        //执行查询操作
        ordersService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 店铺出餐状态
     * @param smStatus
     * @param id
     * @return
     */
    @PostMapping("/smStatus/{smStatus}")
    public R changeSmStatus(@PathVariable int smStatus,String id){
        Orders orders = new Orders();
        orders.setId(Long.parseLong(id));
        orders.setSmStatus(smStatus);
        orders.setSmTime(LocalDateTime.now());
        ordersService.updateById(orders);
        return R.success("店铺正在出餐");
    }

    /**
     * 骑手送餐状态
     * @param deStatus
     * @param id
     * @return
     */
    @PostMapping("/deStatus/{deStatus}")
    public R changeDeStatus(@PathVariable int deStatus,String id){
        Orders orders = new Orders();
        orders.setId(Long.parseLong(id));
        orders.setDeStatus(deStatus);
        orders.setDeTime(LocalDateTime.now());
        ordersService.updateById(orders);
        return R.success("骑手开始配送");
    }

    /**
     * 确认or取消收货
     * @param ocStatus
     * @param id
     * @return
     */
    @PostMapping("/ocStatus/{ocStatus}")
    public R changeOcStatus(@PathVariable int ocStatus,String id){
        Orders orders = new Orders();
        orders.setId(Long.parseLong(id));
        //取消收货分为顾客和商店， 1为顾客取消，2为商店取消 3为确认收货
        orders.setOcStatus(ocStatus);
        orders.setOcTime(LocalDateTime.now());
        ordersService.updateById(orders);
        return R.success("已取消订单");
    }

    /**
     * 修改评分
     * @param cakeRating
     * @param id
     * @return
     */
    @PostMapping("/cakeRating/{cakeRating}")
    public R changeCakeRating(@PathVariable Double cakeRating,String id){
        Orders orders = new Orders();
        orders.setId(Long.parseLong(id));
        orders.setCakeRating(cakeRating);
        ordersService.updateById(orders);
        return R.success("评价成功~");
    }
    /**
     * 根据id批量删除订单
     * @param ids
     * @return
     */
    @DeleteMapping("deleteOrders")
    public R delete(String ids){
        String[] list = ids.split(",");
        for (String id : list) {
            ordersService.removeById(Long.parseLong(id));
        }
        return R.success("删除成功");
    }
}
