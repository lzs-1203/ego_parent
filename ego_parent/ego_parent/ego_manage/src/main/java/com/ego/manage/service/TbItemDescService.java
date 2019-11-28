package com.ego.manage.service;

import com.ego.commons.pojo.EgoResult;

public interface TbItemDescService {
    /**
     * 根据主键查询，返回值要包含TbItemDesc对象
     * @param id
     * @return
     */
    EgoResult selectById(Long id);
}
