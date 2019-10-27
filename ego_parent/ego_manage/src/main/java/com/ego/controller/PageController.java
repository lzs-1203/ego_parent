package com.ego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/6
 * @Description: com.ego.controller
 * @version: 1.0
 */
@Controller
public class PageController {

    /*首页面展示*/
    @RequestMapping("/")
    public String showMain(){
        return "index";
    }
    /*使用restful格式进行数据传输的时候，进行数据的获取形式*/
    @RequestMapping("{page}")
    public String getPage(@PathVariable String page){
        return page;
    }

    /*商品修改的页面*/
    @RequestMapping("/rest/page/item-edit")
    public String getEditPage(){
        return "item-edit";
    }

}
