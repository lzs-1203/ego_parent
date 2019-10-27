package com.ego.portal.controller;

import com.ego.portal.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/12
 * @Description: com.ego.portal
 * @version: 1.0
 */
@Controller
public class PageController {


    @Autowired
    private TbContentService tbContentService;

    @GetMapping("/")
    public String welcome(Model model){
        System.out.println("执行了这个程序");
        String s = tbContentService.showBigImg();
        System.out.println(s);
        model.addAttribute("ad1",s);
        return "index";
    }
}
