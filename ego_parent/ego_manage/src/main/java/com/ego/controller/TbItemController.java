package com.ego.controller;

import com.ego.commoms.pojo.EasyUiDataGrid;
import com.ego.commoms.pojo.EgoResult;
import com.ego.pojo.TbItem;
import com.ego.servcie.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/6
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TbItemController {

    @Autowired
    TbItemService tbItemService;

    /*分页展示所有的商品*/
    @RequestMapping("/item/list")
    public EasyUiDataGrid showDataGrid(int page, int rows){
        return tbItemService.findAll(page, rows);
    }

    /*删除操作*/
    @RequestMapping("/rest/item/delete")
    public EgoResult updateStatus3(String[] ids){
        return tbItemService.updateStatus(ids, (byte)3);
    }

    /*下架操作*/
    @RequestMapping("/rest/item/instock")
    public EgoResult updateStatus2(String[] ids){
        return tbItemService.updateStatus(ids, (byte)2);
    }

    /*上架操作*/
    @RequestMapping("/rest/item/reshelf")
    public EgoResult updateStatus1(String[] ids){

        return tbItemService.updateStatus(ids, (byte)1);
    }

    /*增加商品*/
    @RequestMapping("/item/save")
    public EgoResult saveItem(TbItem tbItem, String desc , String itemParams){
        return tbItemService.insertItem(tbItem, desc , itemParams);
    }

    @RequestMapping("/rest/item/update")
    public EgoResult updateItem(TbItem tbItem, String desc, Long itemParamId ,String itemParams){
        return tbItemService.updateItem(tbItem, desc,itemParamId, itemParams);
    }

}
