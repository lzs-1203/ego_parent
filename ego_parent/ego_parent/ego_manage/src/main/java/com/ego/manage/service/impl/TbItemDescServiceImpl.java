package com.ego.manage.service.impl;

import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.manage.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbItemDescServiceImpl implements TbItemDescService {
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;
    @Override
    public EgoResult selectById(Long id) {
        TbItemDesc itemDesc = tbItemDescDubboService.selectById(id);
        if(itemDesc!=null){
            return EgoResult.ok(itemDesc);
        }
        return EgoResult.error("查询失败");
    }
}
