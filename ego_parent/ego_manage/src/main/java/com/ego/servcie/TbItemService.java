package com.ego.servcie;

import com.ego.commoms.pojo.EasyUiDataGrid;
import com.ego.commoms.pojo.EgoResult;
import com.ego.pojo.TbItem;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/6
 * @Description: com.ego.servcie
 * @version: 1.0
 */
public interface TbItemService {

    /*显示所有的商品信息，分页查询*/
    EasyUiDataGrid findAll(int page, int rows);

    /*更新商品的状态*/
    EgoResult updateStatus(String[] ids, Byte status);

    /*增加新的商品*/
    EgoResult insertItem(TbItem tbItem ,String desc ,String itemParams);

    /*更新商品的信息*/
    EgoResult updateItem(TbItem tbItem, String desc,Long itemParamId,String itemParams);

}
