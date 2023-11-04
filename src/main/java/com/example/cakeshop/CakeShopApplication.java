package com.example.cakeshop;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j//打印日志
@SpringBootApplication
@ServletComponentScan//用于过滤器资源放行生效
@EnableTransactionManagement//开启事务处理的注解
//@MapperScan("com.example.cakeshop.mapper")
public class CakeShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(CakeShopApplication.class, args);
        log.info("项目启动成功");//输出日志
    }

}
