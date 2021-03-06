package com.ego.dubbo.service.impl;
import com.ego.dubbo.service.TbItemParamItemDubboServcie;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/8
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbItemParamItemDubboServcieImpl implements TbItemParamItemDubboServcie {


    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;


    @Override
    public TbItemParamItem selectByItemId(Long itemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
