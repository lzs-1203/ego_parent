package com.ego.item.controller;

import com.ego.item.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/12
 * @Description: com.ego.item.controller
 * @version: 1.0
 */
@RestController
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/rest/itemcat/all")
    @CrossOrigin
    public Map<String , Object> showMenu(){
        return itemCatService.showMenu();
    }
}
