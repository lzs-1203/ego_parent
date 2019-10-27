package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/6
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {

    @Autowired
    TbItemDescMapper tbItemDescMapper;

    @Override
    public TbItemDesc selectById(Long id) {
        return tbItemDescMapper.selectByPrimaryKey(id);
    }
}
