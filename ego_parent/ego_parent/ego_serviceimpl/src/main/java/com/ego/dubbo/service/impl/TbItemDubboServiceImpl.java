package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TbItemDubboServiceImpl implements TbItemDubboService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Override
    public List<TbItem> selectByPage(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        //查询全部
        List<TbItem> list = tbItemMapper.selectByExample(null);
        // 构造方法参数必须是查询全部的结果。否则无法知道给哪个sql后面拼接limit
        //PageInfo是分页查询所有查询结果封装的类，所有的结果都从这个类取
        PageInfo<TbItem> pi = new PageInfo<>(list);
        return pi.getList();
    }

    @Override
    public Long selectCount() {
        return tbItemMapper.countByExample(null);
    }

    @Override
    @Transactional
    public int insert(TbItem item, TbItemDesc desc) throws DaoException {
        int index = tbItemMapper.insert(item);
        if(index==1){
            int index2 = tbItemDescMapper.insert(desc);
            if(index2==1){
                return 1;
            }
        }
        throw new DaoException("新增失败");
    }

    @Override
    @Transactional
    public int update(TbItem item, TbItemDesc desc) throws DaoException {
        int index = tbItemMapper.updateByPrimaryKeySelective(item);
        if(index==1){
            int index2 = tbItemDescMapper.updateByPrimaryKeySelective(desc);
            if(index2==1){
                return 1;
            }
        }
        throw  new DaoException("修改失败");
    }

    @Override
    @Transactional
    public int updateStatusByIds(Byte status, Long[] ids) throws DaoException {
        Date date = new Date();
        int index = 0 ;
        for(Long id:ids){
            TbItem item = new TbItem();
            item.setId(id);
            item.setStatus(status);
            item.setUpdated(date);
            index+=tbItemMapper.updateByPrimaryKeySelective(item);
        }

        if(index==ids.length){
            return 1;
        }

        throw  new DaoException("批量更新失败");
    }
}
