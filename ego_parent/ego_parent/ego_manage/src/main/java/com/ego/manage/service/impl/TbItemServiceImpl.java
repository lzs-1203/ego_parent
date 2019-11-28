package com.ego.manage.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.commons.pojo.EasyUIDatagrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboService;
    @Override
    public EasyUIDatagrid showItem(int page, int rows) {
        EasyUIDatagrid datagrid = new EasyUIDatagrid();
        datagrid.setTotal(tbItemDubboService.selectCount());
        datagrid.setRows(tbItemDubboService.selectByPage(page,rows));
        return datagrid;
    }

    @Override
    public EgoResult updateStatus(Byte status, String ids) {
        String[] strArr = ids.split(",");
        Long[] longArr = new Long[strArr.length];
        for(int i =0;i<strArr.length;i++){
            longArr[i] = Long.parseLong(strArr[i]);
        }
        try {
            int index = tbItemDubboService.updateStatusByIds(status, longArr);
            if(index==1){
                return EgoResult.ok();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return EgoResult.error("操作失败");
    }

    @Override
    public EgoResult insert(TbItem item, String desc) {

        Date date = new Date();
        long id = IDUtils.genItemId();
        //tb_item 表数据设置
        item.setId(id);
        item.setStatus((byte)1);
        item.setCreated(date);
        item.setUpdated(date);
        //tb_item_desc数据
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(id);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);


        String errorMsg = "";
        try {
            int index = tbItemDubboService.insert(item, itemDesc);
            if(index==1){
                return EgoResult.ok();
            }
        } catch (DaoException e) {
            e.printStackTrace();
            errorMsg = e.getMessage();

        }
        return EgoResult.error(errorMsg);
    }

    @Override
    public EgoResult update(TbItem item, String desc) {

        Date date = new Date();
        //item数据补全
        item.setUpdated(date);

        //item_desc数据
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setUpdated(date);
        itemDesc.setItemDesc(desc);

        try {
            int index = tbItemDubboService.update(item, itemDesc);
            if(index==1){
                return EgoResult.ok();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return EgoResult.error("修改失败");
    }
}
