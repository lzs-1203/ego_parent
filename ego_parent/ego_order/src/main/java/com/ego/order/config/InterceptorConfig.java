package com.ego.order.config;

import com.ego.order.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 一个类实现了WebMvcConfigurrer之后就表示这个类是相当于springmvc配置文件
 * @Auther: Constant.Wang
 * @Date: 2019/8/20
 * @Description: com.ego.order.config
 * @version: 1.0
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }
}
