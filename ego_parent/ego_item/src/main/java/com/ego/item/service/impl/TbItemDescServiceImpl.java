package com.ego.item.service.impl;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.item.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/15
 * @Description: com.ego.item.service.impl
 * @version: 1.0
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {

    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Override
    public String showDesc(Long id) {
        TbItemDesc tbItemDesc = tbItemDescDubboService.selectById(id);
        return tbItemDesc.getItemDesc();
    }
}
