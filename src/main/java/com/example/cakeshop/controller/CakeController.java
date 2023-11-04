package com.example.cakeshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cakeshop.common.R;
import com.example.cakeshop.dto.CakeDto;
import com.example.cakeshop.entity.Cake;
import com.example.cakeshop.entity.Comment;
import com.example.cakeshop.entity.Orders;
import com.example.cakeshop.service.CakeService;
import com.example.cakeshop.service.CommentService;
import com.example.cakeshop.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/cake")
public class CakeController {

    @Autowired
    private CakeService cakeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private OrdersService ordersService;

    /**
     * 新增蛋糕
     * @param cake
     * @return
     */
    @PostMapping("/addCake")
    public R<String> save(@RequestBody Cake cake){//@RequestBody将前端的json数据封装
        //保存没有的内容
        if (cake.getCakeImg() == null){
            cake.setCakeImg("https://picture-storage-zsm.oss-cn-hangzhou.aliyuncs.com/2023/01/30/nothing.png");
        }
        cake.setCakeStatus(1);
        cake.setCakeRec(0);
        //2.根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Cake> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cake::getCakeName,cake.getCakeName());
        Cake ca = cakeService.getOne(queryWrapper);
        //3.查询不到且推荐没满4则执行新增
        if(ca == null){
            cakeService.save(cake);
            return R.success("新增蛋糕成功");
        } else{
            return R.error("此蛋糕已存在");
        }
    }

//    /**
//     * 蛋糕信息分页模糊查询
//     * @param page
//     * @param pageSize
//     * @param cakeName
//     * @param cakeType
//     * @param cakeIntro
//     * @return
//     */
//    @GetMapping("/page")
//    public R<Page> page(int page, int pageSize, String cakeName, String cakeType, String cakeIntro){
//        //构造分页构造器
//        Page pageInfo = new Page(page,pageSize);
//        //构造条件构造器
//        LambdaQueryWrapper<Cake> queryWrapper = new LambdaQueryWrapper<>();
//        //添加搜索条件//模糊查询
//        queryWrapper.like(StringUtils.isNotEmpty(cakeName),Cake::getCakeName,cakeName);
//        queryWrapper.like(StringUtils.isNotEmpty(cakeType),Cake::getCakeType,cakeType);
//        queryWrapper.like(StringUtils.isNotEmpty(cakeIntro),Cake::getCakeIntro,cakeIntro);
//        //添加排序条件//时间排序
//        queryWrapper.orderByDesc(Cake::getUpdateTime);
//        //执行查询
//        cakeService.page(pageInfo,queryWrapper);
//        return R.success(pageInfo);
//    }

    /**
     * 蛋糕信息分页模糊查询
     * @param page
     * @param pageSize
     * @param cakeName
     * @param cakeType
     * @param cakeIntro
     * @return
     */
    @GetMapping("/page")
    public R<Page> pageFront(int page, int pageSize, String cakeName, String cakeType, String cakeIntro){
        //构造分页构造器
        Page<Cake> pageInfo = new Page<>(page,pageSize);
        Page<CakeDto> cakeDtoInfo = new Page<>();
        //构造条件构造器
        LambdaQueryWrapper<Cake> queryWrapper = new LambdaQueryWrapper<>();
        //添加搜索条件//模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(cakeName),Cake::getCakeName,cakeName);
        queryWrapper.like(StringUtils.isNotEmpty(cakeType),Cake::getCakeType,cakeType);
        queryWrapper.like(StringUtils.isNotEmpty(cakeIntro),Cake::getCakeIntro,cakeIntro);
        //添加排序条件//时间排序
        queryWrapper.orderByDesc(Cake::getUpdateTime);
        //执行查询
        cakeService.page(pageInfo,queryWrapper);
        //查询出的对象数据从pageInfo拷贝进cakeDtoInfo,过滤掉records
        BeanUtils.copyProperties(pageInfo,cakeDtoInfo,"records");
        //对象数据放入records集合
        List<Cake> records = pageInfo.getRecords();
        //遍历？忘了
        List<CakeDto> list = records.stream().map((item) -> {
            CakeDto cakeDto = new CakeDto();
            BeanUtils.copyProperties(item,cakeDto);
            //蛋糕id
            Long cakeId = item.getId();
            //构造条件构造器
            LambdaQueryWrapper<Orders> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(Orders::getCakeId,cakeId);
            //根据id查询蛋糕的订单数
            List<Map<String, Object>> list1 = ordersService.listMaps(queryWrapper2);
            //结果赋值进cakeDto的对应字段
            cakeDto.setCakeTotal((int) list1.size());
            if (list1.size() == 0){
                //没有该蛋糕的订单那就自定义评分
                cakeDto.setCakeRating(5.0);
            }else {
                Double sum = 0.0;
                for (Map<String, Object> map : list1) {
                    sum += (Double) map.get("cake_rating");
                }
                //保留两位小数
                Double one = sum/list1.size();
                String str = String.format("%.2f", one);
                Double avg_rating = Double.parseDouble(str);
                //把求出来的平均评分赋值到对应字段
                cakeDto.setCakeRating(avg_rating);
            }
            return cakeDto;
        }).collect(Collectors.toList());
        cakeDtoInfo.setRecords(list);
        return R.success(cakeDtoInfo);
    }

    /**
     * 根据id修改蛋糕信息
     * @param cake
     * @return
     */
    @PutMapping("update")
    public R<String> update(@RequestBody Cake cake){//@RequestBody将前端的json数据封装HttpServletRequest request取当前登录用户
        //根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Cake> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cake::getCakeName,cake.getCakeName());
        LambdaQueryWrapper<Cake> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(Cake::getCakeRec,cake.getCakeRec());
        //根据蛋糕名字查询数据库//查看此蛋糕是否已经存在
        Cake ca = cakeService.getOne(queryWrapper);
        //查询数据库中被推荐的蛋糕有多少条，超过4条则不能更新
        long rec = cakeService.count(queryWrapper2);
        //根据ID查询数据库中本条数据更新前的信息，用于判断条件
        Cake ca2 = cakeService.getById(cake.getId());
        if (ca != null && !ca.getId().equals(cake.getId())) {
            return R.error("此蛋糕已存在");
        }
        if (rec > 3 && !Objects.equals(cake.getCakeRec(), ca2.getCakeRec())) {
            return R.error("推荐蛋糕已满4个，不能继续推荐");
        }
        //执行更新
        cakeService.updateById(cake);
        return R.success("蛋糕信息修改成功");
    }

//    /**
//     * 根据id查询蛋糕信息
//     * 对于更新操作，这里只调用了查询，用于更新时的数据回显，更新操作在update方法中复用即可完成
//     * @param id
//     * @return
//     */
//    @GetMapping("queryCake/{id}")
//    public R<Cake> getById(@PathVariable Long id){//@PathVariable取请求路径里面的id
//        //执行查询操作
//        Cake cake = cakeService.getById(id);
//        if (cake != null){
//            return R.success(cake);
//        }
//        return R.error("没有查询到对应的蛋糕信息");
//    }

    /**
     * 根据id查询蛋糕信息
     * 对于更新操作，这里只调用了查询，用于更新时的数据回显，更新操作在update方法中复用即可完成
     * @param id
     * @return
     */
    @GetMapping("queryCake/{id}")
    public R<CakeDto> getCakeById(@PathVariable Long id){//@PathVariable取请求路径里面的id
        //执行查询操作
        Cake cake = cakeService.getById(id);
        CakeDto cakeDto = new CakeDto();
        BeanUtils.copyProperties(cake,cakeDto,"records");
        //蛋糕id
        Long cakeId = id;
        //构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(Orders::getCakeId,cakeId);
        //根据id查询蛋糕的订单数
        List<Map<String, Object>> list1 = ordersService.listMaps(queryWrapper2);
        //结果赋值进cakeDto的对应字段
        cakeDto.setCakeTotal((int) list1.size());
        if (list1.size() == 0){
            //没有该蛋糕的订单那就自定义评分
            cakeDto.setCakeRating(5.0);
        }else {
            Double sum = 0.0;
            for (Map<String, Object> map : list1) {
                sum += (Double) map.get("cake_rating");
            }
            //保留两位小数
            Double one = sum/list1.size();
            String str = String.format("%.2f", one);
            Double avg_rating = Double.parseDouble(str);
            //把求出来的平均评分赋值到对应字段
            cakeDto.setCakeRating(avg_rating);
        }
        return R.success(cakeDto);
    }

    /**
     * 修改蛋糕售罄状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R changeStatus(@PathVariable int status,String ids){
        String[] idList = ids.split(",");
        for (String id : idList) {
            Cake cake = new Cake();
            cake.setId(Long.parseLong(id));
            cake.setCakeStatus(status);
            cakeService.updateById(cake);
        }
        return R.success("更新状态成功");
    }
    /**
     * 修改蛋糕推荐
     * @param cakeRec
     * @param ids
     * @return
     */
    @PostMapping("/cakeRec/{cakeRec}")
    public R changeCakeRec(@PathVariable int cakeRec,String ids){
        String[] idList = ids.split(",");
        for (String id : idList) {
            Cake cake = new Cake();
            LambdaQueryWrapper<Cake> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Cake::getCakeRec,1);
            //查询数据库中被设为推荐的蛋糕有多少条
            long rec = cakeService.count(queryWrapper);
            if (rec <= 4 && cakeRec == 0){
                cake.setId(Long.parseLong(id));
                cake.setCakeRec(cakeRec);
                cakeService.updateById(cake);
                return R.success("取消推荐成功");
            }else if (rec >= 4 && cakeRec == 1){
                return R.error("推荐蛋糕已经满4个，不能继续推荐");
            }else {
                cake.setId(Long.parseLong(id));
                cake.setCakeRec(cakeRec);
                cakeService.updateById(cake);
                return R.success("推荐蛋糕成功");
            }
        }
        return R.error("未知错误");
    }

    /**
     * 根据id批量删除蛋糕
     * 蛋糕删除，评论也要删除
     * @param ids
     * @return
     */
    @DeleteMapping("deleteCake")
    public R delete(String ids){
        String[] list = ids.split(",");
        for (String id : list) {
            //删除蛋糕
            cakeService.removeById(Long.parseLong(id));
            //删除评论
            LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Comment::getCakeId, id);
            commentService.remove(wrapper);
        }
        return R.success("删除成功");
    }
}
