package com.ego.item.controller;

import com.ego.item.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/15
 * @Description: com.ego.item.controller
 * @version: 1.0
 */
@Controller
public class ItemController {

    @Autowired
    private TbItemService tbItemService;

    @GetMapping("/item/{id}.html")
    public String showItemDetails(@PathVariable Long id, Model model){
        model.addAttribute("item",tbItemService.showImage(id));
        return "item";
    }

}
