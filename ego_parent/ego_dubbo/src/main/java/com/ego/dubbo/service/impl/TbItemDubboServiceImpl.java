package com.ego.dubbo.service.impl;

import com.ego.commoms.utils.IDUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.commoms.pojo.EasyUiDataGrid;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/6
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbItemDubboServiceImpl implements TbItemDubboService {

    @Autowired
    TbItemMapper tbItemMapper;

    @Autowired
    TbItemDescMapper tbItemDescMapper;

    @Autowired
    TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public EasyUiDataGrid selectAll(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        EasyUiDataGrid dataGrid = new EasyUiDataGrid();
        dataGrid.setRows(pageInfo.getList());
        dataGrid.setTotal(pageInfo.getTotal());
        return dataGrid;
    }

    @Override
    public List<TbItem> selectAll2() {
        List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
        return list;
    }

    @Override
    @Transactional
    public int updataById(String[] ids, Byte status) {
        Date date = new Date();
        int index = 0;
        for(int i = 0 ; i < ids.length ; i++){
            TbItem tbItem = new TbItem();
            tbItem.setId(Long.parseLong(ids[i]));
            tbItem.setStatus(status);
            tbItem.setUpdated(date);
            index += tbItemMapper.updateByPrimaryKeySelective(tbItem);
        }
        //精确判断
        if(index == ids.length){
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public long insertItem(TbItem tbItem, TbItemDesc tbItemDesc , TbItemParamItem param) {
        //生成商品编号和详情编号
        long id = IDUtils.genItemId();
        //获取当前的时间
        Date date = new Date();
        /*商品添加*/
        tbItem.setId(id);
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        tbItem.setStatus((byte)1);
        int i = tbItemMapper.insertSelective(tbItem);
        /*商品类型添加*/
        tbItemDesc.setItemId(id);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);

        i += tbItemDescMapper.insertSelective(tbItemDesc);

        param.setItemId(id);
        param.setCreated(date);
        param.setUpdated(date);
        i += tbItemParamItemMapper.insertSelective(param);
        if(i == 3){
            return id;
        }
        return 0;
    }

    @Override
    @Transactional
    public int updateItem(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem paramItem) {
        Date date = new Date();
        tbItemDesc.setUpdated(date);
        tbItem.setUpdated(date);
        paramItem.setUpdated(date);
        int i = tbItemMapper.updateByPrimaryKeySelective(tbItem);
        i += tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
        i += tbItemParamItemMapper.updateByPrimaryKeySelective(paramItem);
        if(i == 3){
            return 1;
        }
        return 0;
    }

    @Override
    public TbItem selectById(Long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }

}
