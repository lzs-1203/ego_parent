package com.ego.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 显示页面的控制器
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public String welcome() {
        return "index";
    }

    //称为restful请求方式
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }

    @RequestMapping("/rest/page/item-edit")
    public String showItemUpdate() {
        return "item-edit";
    }
}
