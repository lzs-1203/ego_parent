package com.ego.dubbo.service;


import com.ego.pojo.TbItemDesc;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/6
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbItemDescDubboService {

    /**
     * 查询指定的商品信息
     * @param id
     * @return
     */
    TbItemDesc selectById(Long id);
}
