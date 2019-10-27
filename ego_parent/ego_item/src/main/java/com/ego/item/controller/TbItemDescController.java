package com.ego.item.controller;

import com.ego.item.service.TbItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/15
 * @Description: com.ego.item.controller
 * @version: 1.0
 */
@Controller
public class TbItemDescController {

    @Autowired
    private TbItemDescService tbItemDescService;

    @GetMapping("/item/desc/{id}.html")
    @ResponseBody
    public String showDesc(@PathVariable Long id){
        return tbItemDescService.showDesc(id);
    }

}
