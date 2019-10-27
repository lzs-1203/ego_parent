package com.ego.dubbo.service;

import com.ego.commoms.pojo.EasyUiDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/6
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbItemDubboService {

    /**
     * 查询所有的商品，通过easyui显示
     * @param page
     * @param rows
     * @return
     */
    EasyUiDataGrid selectAll(int page , int rows);

    /**
     * 查询所有的商品信息，并以集合的形式返回
     * @return
     */
    List<TbItem> selectAll2();

    /**
     * 更新数据
     * @return
     */
    int updataById(String[] ids, Byte status);


    /**
     * 新增数据
     * @param tbItem
     * @param tbItemDesc
     * @return
     */
    long insertItem(TbItem tbItem, TbItemDesc tbItemDesc , TbItemParamItem tbItemParam);


    /**
     * 更新数据
     * @param tbItem
     * @param tbItemDesc
     * @return
     */
    int updateItem(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem paramItem);

    /**
     * 根据主键进行查询商品的信息
     * @param id
     * @return
     */
    TbItem selectById(Long id);


}
