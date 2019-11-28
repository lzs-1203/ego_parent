package com.ego.dubbo.service;

import com.ego.pojo.TbItemCat;

import java.util.List;

public interface TbItemCatDubboService {
    /**
     * 根据父id查询所有正常子菜单信息。（删除除外）
     * @param parent_id 父菜单id
     * @return 所有子菜单
     */
    List<TbItemCat> selectByPid(Long parent_id);
}
