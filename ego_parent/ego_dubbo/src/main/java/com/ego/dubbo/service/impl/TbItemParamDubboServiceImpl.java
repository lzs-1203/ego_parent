package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {

    @Autowired
    TbItemParamMapper tbItemParamMapper;


    @Override
    public List<TbItemParam> selectAllTbItemParam(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        PageInfo<TbItemParam> pi = new PageInfo<>(list);
        return pi.getList();
    }

    @Override
    public Long selectCount() {
        return tbItemParamMapper.countByExample(new TbItemParamExample());
    }

    @Override
    public TbItemParam selectByCatId(Long itemCatId) {
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
        if( list != null && list.size() > 0 ){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insert(TbItemParam param) {
        Date date = new Date();
        param.setCreated(date);
        param.setUpdated(date);
        int i = tbItemParamMapper.insertSelective(param);
        return i;
    }

    @Override
    public int deleteById(String[] ids) {
        int index = 0;
        for(String str : ids){
            index += tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(str));
        }
        if(index == ids.length){
            return 1;
        }
        return 0;
    }

}
