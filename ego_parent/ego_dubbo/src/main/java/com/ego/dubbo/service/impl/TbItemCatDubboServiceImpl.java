package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbItemCatDubboServiceImpl implements TbItemCatDubboService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<TbItemCat> selectBypid(Long pid) {
        TbItemCatExample example = new TbItemCatExample();
        example.setOrderByClause("sort_order asc");
        example.createCriteria().andParentIdEqualTo(pid);
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);

        return list;

    }

    @Override
    public TbItemCat selectById(Long id) {
        return tbItemCatMapper.selectByPrimaryKey(id);
    }
}
