package com.ego.servcie.impl;

import com.ego.commoms.pojo.EasyUiDataGrid;
import com.ego.commoms.pojo.EgoResult;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.pojo.TbItemParam;
import com.ego.servcie.TbItemParamServcie;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.servcie.impl
 * @version: 1.0
 */
@Service
public class TbItemParamServcieImpl implements TbItemParamServcie {

    @Reference
    TbItemParamDubboService tbItemParamDubboService;

    @Reference
    TbItemCatDubboService tbItemCatDubboService;

    @Override
    public EasyUiDataGrid findAllTbItemParam(int page, int rows) {
        List<TbItemParam> list = tbItemParamDubboService.selectAllTbItemParam(page, rows);
        ArrayList<TbItemParamChild> childrenList = new ArrayList<>();
        for(TbItemParam tip : list){
            TbItemParamChild tipc = new TbItemParamChild();
            BeanUtils.copyProperties(tip, tipc);
            tipc.setItemCatName(tbItemCatDubboService.selectById(tip.getItemCatId()).getName());
            childrenList.add(tipc);
        }
        Long count = tbItemParamDubboService.selectCount();
        EasyUiDataGrid easyUiDataGrid = new EasyUiDataGrid();
        easyUiDataGrid.setRows(childrenList);
        easyUiDataGrid.setTotal(count);
        return easyUiDataGrid;
    }

    @Override
    public EgoResult deleteById(String[] ids) {
        int i = tbItemParamDubboService.deleteById(ids);
        if( i == 1 ){
            return EgoResult.ok();
        }
        return EgoResult.error("删除失败");
    }

    @Override
    public EgoResult findByCatId(Long itemCatId) {
        TbItemParam tbItemParam = tbItemParamDubboService.selectByCatId(itemCatId);
        return EgoResult.ok(tbItemParam);
    }

    @Override
    public EgoResult saveItemParam(TbItemParam param, Long itemCatId) {
        param.setItemCatId(itemCatId);
        int insert = tbItemParamDubboService.insert(param);
        if(insert == 1 ){
            return EgoResult.ok();
        }
        return EgoResult.error("添加失败");
    }
}
