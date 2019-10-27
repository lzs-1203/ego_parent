package com.ego.controller;

import com.ego.commoms.pojo.EgoResult;
import com.ego.servcie.TbItemParamItemservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/8
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TbItemParamItemController {

    @Autowired
    TbItemParamItemservice tbItemParamItemservice;

    @GetMapping("/rest/item/param/item/query/{itemId}")
    EgoResult showByItemId(@PathVariable Long itemId){
        return tbItemParamItemservice.findByItemId(itemId);
    }

}
