package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/12
 * @Description: com.ego.portal
 * @version: 1.0
 */
@SpringBootApplication
@EnableDubbo
public class PortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }
}
