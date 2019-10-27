package com.ego.item.controller;

import com.ego.item.service.impl.TbItemParamItemServcieImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/15
 * @Description: com.ego.item.controller
 * @version: 1.0
 */
@RestController
public class TbItemParamItemController {

    @Autowired
    private TbItemParamItemServcieImpl tbItemParamItemServcie;

    @GetMapping("/item/param/{itemId}.html")
    public String showParam(@PathVariable Long itemId){
        return tbItemParamItemServcie.showParam(itemId);
    }

}
