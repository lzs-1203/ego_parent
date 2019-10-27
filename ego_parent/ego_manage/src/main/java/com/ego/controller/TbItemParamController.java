package com.ego.controller;

import com.ego.commoms.pojo.EasyUiDataGrid;
import com.ego.commoms.pojo.EgoResult;
import com.ego.pojo.TbItemParam;
import com.ego.servcie.TbItemParamServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TbItemParamController {

    @Autowired
    private TbItemParamServcie tbItemParamServcie;

    @GetMapping("/item/param/list")
    public EasyUiDataGrid showAllTbItemParam(int page, int rows){
        return tbItemParamServcie.findAllTbItemParam(page, rows);
    }

    @PostMapping("/item/param/delete")
    public EgoResult deleteItemParam(String[] ids){
        return tbItemParamServcie.deleteById(ids);
    }

    @GetMapping("/item/param/query/itemcatid/{itemCatId}")
    public EgoResult findByCatId(@PathVariable Long itemCatId){
        return tbItemParamServcie.findByCatId(itemCatId);
    }

    @PostMapping("/item/param/save/{itemCatId}")
    public EgoResult saveItemParam(TbItemParam param, @PathVariable Long itemCatId){
        return tbItemParamServcie.saveItemParam(param, itemCatId);
    }

}
