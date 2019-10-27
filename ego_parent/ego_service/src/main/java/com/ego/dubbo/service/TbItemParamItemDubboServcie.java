package com.ego.dubbo.service;

import com.ego.pojo.TbItemParamItem;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/8
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbItemParamItemDubboServcie {


    /**
     * 查询指定的商品编号的商品规格信息（具体的信息）
     * @param itemId
     * @return
     */
    TbItemParamItem selectByItemId(Long itemId);
}
