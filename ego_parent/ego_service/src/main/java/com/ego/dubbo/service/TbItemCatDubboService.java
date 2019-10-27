package com.ego.dubbo.service;


import com.ego.pojo.TbItemCat;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbItemCatDubboService {

    /**
     * 显示树状目录，异步实现查询
     * @param pid
     * @return
     */
    List<TbItemCat> selectBypid(Long pid);


    /**
     * 通过目录的id进行查询
     * @param id
     * @return
     */
    TbItemCat selectById(Long id);

}
