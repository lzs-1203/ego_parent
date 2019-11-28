package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

public interface TbItemDescDubboService {
    /**
     * 根据主键进行查询
     * @param id 主键
     * @return
     */
    TbItemDesc selectById(Long id);
}
