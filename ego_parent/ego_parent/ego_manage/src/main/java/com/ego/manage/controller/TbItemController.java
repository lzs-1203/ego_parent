package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TbItemController {
    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDatagrid showItem(int page,int rows){
        return tbItemService.showItem(page,rows);
    }

    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public EgoResult delete(String ids){
        return tbItemService.updateStatus((byte)3,ids);
    }
    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public EgoResult instock(String ids){
        return tbItemService.updateStatus((byte)2,ids);
    }
    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public EgoResult reshelf(String ids){
        return tbItemService.updateStatus((byte)1,ids);
    }


    /**
     * 新增
     * @param item
     * @param desc
     * @return
     */
    @PostMapping("/item/save")
    @ResponseBody
    public EgoResult insert(TbItem item,String desc){
        return tbItemService.insert(item,desc);
    }

    /**
     * 修改
     * @param item
     * @param desc
     * @return
     */
    @PostMapping("/rest/item/update")
    @ResponseBody
    public EgoResult update(TbItem item,String desc){
        return tbItemService.update(item,desc);
    }
}
