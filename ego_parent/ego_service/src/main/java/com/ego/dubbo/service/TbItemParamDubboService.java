package com.ego.dubbo.service;

import com.ego.pojo.TbItemParam;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbItemParamDubboService {

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    List<TbItemParam> selectAllTbItemParam(int page , int rows);

    /**
     * 删除商品参数规格信息
     * @param ids
     * @return
     */
    int deleteById(String[] ids);

    /**
     * 查询总的数据数目
     * @return
     */
    Long selectCount();

    /**
     * 通过商品目录id进行查询
     * @param itemCatId
     * @return
     */
    TbItemParam selectByCatId(Long itemCatId);

    /**
     * 增加新的参数规格信息
     * @param param
     * @return
     */
    int insert (TbItemParam param);
}
