package com.ego.servcie.impl;

import com.ego.commoms.pojo.EgoResult;
import com.ego.dubbo.service.TbItemParamItemDubboServcie;
import com.ego.pojo.TbItemParamItem;
import com.ego.servcie.TbItemParamItemservice;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/8
 * @Description: com.ego.servcie.impl
 * @version: 1.0
 */
@Service
public class TbItemParamItemserviceImpl implements TbItemParamItemservice {


    @Reference
    TbItemParamItemDubboServcie tbItemParamItemDubboServcie;


    @Override
    public EgoResult findByItemId(Long itemId) {
        TbItemParamItem paramItem = tbItemParamItemDubboServcie.selectByItemId(itemId);
        if(paramItem != null){
            return EgoResult.ok(paramItem);
        }
        return  EgoResult.error("不存在");
    }
}
