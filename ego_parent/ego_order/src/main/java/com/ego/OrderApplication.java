package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/20
 * @Description: com.ego
 * @version: 1.0
 */
@SpringBootApplication
@EnableDubbo
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
