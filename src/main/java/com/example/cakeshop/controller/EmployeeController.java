package com.example.cakeshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cakeshop.common.R;
import com.example.cakeshop.entity.Employee;
import com.example.cakeshop.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 添加员工
     * @param employee
     * @return
     */
    @PostMapping("/register")
    public R<String> save(@RequestBody Employee employee){//@RequestBody将前端的json数据封装
        //保存没有的内容
        if (employee.getEmpPassword() == null){
            //将密码进行md5加密处理，默认员工密码为123456
            String password = "123456";
            employee.setEmpPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        }
        if (employee.getEmpAvatar() == null) {
            employee.setEmpAvatar("https://picture-storage-zsm.oss-cn-hangzhou.aliyuncs.com/2023/01/30/2c42142883d541c38ad0e7b13e31dc4efile.png");
        }
        employee.setEmpStatus(1);
        //2.根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getEmpUsername,employee.getEmpUsername());
        LambdaQueryWrapper<Employee> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(Employee::getEmpEmail,employee.getEmpEmail());
        Employee us = employeeService.getOne(queryWrapper);
        Employee el = employeeService.getOne(queryWrapper2);
        //3.查询不到则执行新增
        if(us == null && el == null){
            employeeService.save(employee);
            return R.success("新增员工成功");
        } else if (us != null) {
            return R.error("此账号已经存在");
        }
        return R.error("此邮箱已经存在");
    }

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        System.out.println(employee.toString());
        //1.将页面提交的密码进行md5加密处理
        String password = employee.getEmpPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2.根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getEmpUsername,employee.getEmpUsername());
        Employee emp = employeeService.getOne(queryWrapper);//执行登录。。。getOne,数据库设置唯一，就可以通过这个方法
        //3.查询不到则返回失败
        if(emp == null){
            return R.error("用户不存在");
        }
        //4.密码不一致返回失败
        if (!emp.getEmpPassword().equals(password)){
            return R.error("密码错误");
        }
        //5.账号被禁用返回失败
        Integer status = emp.getEmpStatus();
        if(status != 1){
            return R.error("该用户已被禁用，请联系管理员处理");
        }
        //6.登录成功，将id存入session并返回登录结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 员工信息分页模糊查询
     * @param page
     * @param pageSize
     * @param empUsername
     * @param empNickname
     * @param empEmail
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String empUsername, String empNickname, String empEmail){
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加搜索条件//模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(empUsername),Employee::getEmpUsername,empUsername);
        queryWrapper.like(StringUtils.isNotEmpty(empNickname),Employee::getEmpNickname,empNickname);
        queryWrapper.like(StringUtils.isNotEmpty(empEmail),Employee::getEmpEmail,empEmail);
        //添加搜索条件//返回数据排除管理员信息
        queryWrapper.ne(Employee::getEmpUsername,"administrator");
        //添加排序条件//时间排序
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id修改密码
     * @param
     * @return
     */
    @PutMapping("/editPwd")
    public R<String> editPwd(@RequestBody Map<String,String> map){//@RequestBody将前端的json数据封装
        Employee employee = employeeService.getById(map.get("id"));
        //前台传来的旧密码
        String oldPassword = DigestUtils.md5DigestAsHex(map.get("oldPassword").getBytes());
        //前台传来的新密码
        String newPassword = DigestUtils.md5DigestAsHex(map.get("empPassword").getBytes());
        if (!oldPassword.equals(employee.getEmpPassword())){
            return R.error("旧密码输入错误");
        }
        if (!Objects.equals(employee.getEmpPassword(), newPassword)) {
            employee.setEmpPassword(newPassword);
            employeeService.updateById(employee);
            return R.success("密码修改成功");
        }
        return R.error("新密码和旧密码相同");
    }

    /**
     * 根据id修改用户信息
     * @param employee
     * @return
     */
    @PutMapping("update")
    public R<String> update(@RequestBody Employee employee){//@RequestBody将前端的json数据封装HttpServletRequest request取当前登录用户
        //根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getEmpUsername,employee.getEmpUsername());
        LambdaQueryWrapper<Employee> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(Employee::getEmpEmail,employee.getEmpEmail());
        Employee us = employeeService.getOne(queryWrapper);
        Employee el = employeeService.getOne(queryWrapper2);
        if (us != null && !us.getId().equals(employee.getId())) {
            return R.error("此用户名已存在");
        }
        if (el != null && !el.getId().equals(employee.getId())) {
            return R.error("此邮箱已存在");
        }
        //执行更新
        employeeService.updateById(employee);
        return R.success("用户信息修改成功");
    }

    /**
     * 根据id查询用户信息
     * 对于更新操作，这里只调用了查询，用于更新时的数据回显，更新操作在update方法中复用即可完成
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){//@PathVariable取请求路径里面的id
        //执行查询操作
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("没有查询到对应用户信息");
    }

    /**
     * 更改员工禁用状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R changeStatus(@PathVariable int status,String ids){
        String[] idList = ids.split(",");
        for (String id : idList) {
            Employee employee = new Employee();
            employee.setId(Long.parseLong(id));
            employee.setEmpStatus(status);
            employeeService.updateById(employee);
        }
        return R.success("更新状态成功");
    }

    /**
     * 根据id批量删除用户
     * @param ids
     * @return
     */
    @DeleteMapping("deleteEmp")
    public R delete(String ids){
        String[] list = ids.split(",");
        for (String id : list) {
            employeeService.removeById(Long.parseLong(id));
        }
        return R.success("删除成功");
    }
}
