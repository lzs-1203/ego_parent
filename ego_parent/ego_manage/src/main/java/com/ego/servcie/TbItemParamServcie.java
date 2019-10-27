package com.ego.servcie;

import com.ego.commoms.pojo.EasyUiDataGrid;
import com.ego.commoms.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.servcie
 * @version: 1.0
 */
public interface TbItemParamServcie {


    /*分页查询*/
    EasyUiDataGrid findAllTbItemParam(int page, int rows);
    /*删除商品规格*/
    EgoResult deleteById(String[] ids);


    /*通过商品目录id查询规格参数是否存在*/
    EgoResult findByCatId(Long itemCatId);

    EgoResult saveItemParam(TbItemParam param, Long ItemCatId);

}
