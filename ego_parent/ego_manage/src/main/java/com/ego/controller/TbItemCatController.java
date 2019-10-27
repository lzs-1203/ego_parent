package com.ego.controller;

import com.ego.commoms.pojo.EasyUITree;
import com.ego.servcie.TbItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.controller
 * @version: 1.0
 */
@Controller
public class TbItemCatController {

    @Autowired
    private TbItemCatService tbItemCatService;

    /*商品类目的树状展示*/
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITree> showItemCat(@RequestParam(defaultValue = "0") Long id){
        return tbItemCatService.showItemCat(id);
    }
}
