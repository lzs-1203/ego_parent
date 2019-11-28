package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItem;

public interface TbItemService {
    EasyUIDatagrid showItem(int page,int rows);


    EgoResult updateStatus(Byte status,String ids);

    /**
     * 新增商品信息
     * @param item 商品信息
     * @param desc 商品描述
     * @return 页面需要数据格式
     */
    EgoResult insert(TbItem item, String desc);

    /**
     * 修改
     * @param item
     * @param desc
     * @return
     */
    EgoResult update(TbItem item,String desc);
}
