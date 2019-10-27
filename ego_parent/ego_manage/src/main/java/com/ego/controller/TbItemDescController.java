package com.ego.controller;

import com.ego.commoms.pojo.EgoResult;
import com.ego.servcie.TbItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TbItemDescController {

    @Autowired
    private TbItemDescService tbItemDescService;


    /*通过主键id来得到指定的商品详情*/
    /*使用restful格式，来获取前台发送过来的参数*/
    @GetMapping("/rest/item/query/item/desc/{id}")
    public EgoResult showDesc(@PathVariable Long id){
        return tbItemDescService.findById(id);
    }

}
