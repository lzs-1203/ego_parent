package com.ego.search;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/14
 * @Description: com.ego.search
 * @version: 1.0
 */
@SpringBootApplication
@EnableDubbo
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
