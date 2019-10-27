package com.ego.dubbo.service.impl;

import com.ego.commoms.EgoException.ItemUnderStockException;
import com.ego.commoms.utils.IDUtils;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/20
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbOrderDubboServiceImpl implements TbOrderDubboService {

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public String insert(TbOrder tbOrder, List<TbOrderItem> list, TbOrderShipping tbOrderShipping) throws ItemUnderStockException {
        //订单表
        String orderId = IDUtils.genItemId() + "";
        Date date = new Date();
        tbOrder.setOrderId(orderId);
        tbOrder.setCreateTime(date);
        tbOrder.setUpdateTime(date);
        int i = tbOrderMapper.insertSelective(tbOrder);
        //订单商品信息表
        for(TbOrderItem toi : list){
            toi.setId(IDUtils.genItemId()+"");
            toi.setOrderId(orderId);
            //判断库存的数据
            long itemId = Long.parseLong(toi.getItemId());
            TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
            if(item.getNum() >= toi.getNum()){  //库存数量足够
                TbItem tbItem =  new TbItem();
                tbItem.setId(itemId);
                tbItem.setNum(item.getNum() - toi.getNum());
                tbItem.setUpdated(date);
                tbItemMapper.updateByPrimaryKeySelective(tbItem);
            }else{//库存数量不足
                throw new ItemUnderStockException("库存数量不足，请及时补充！！");
            }
            i += tbOrderItemMapper.insertSelective(toi);
        }
        //订单出货表
        tbOrderShipping.setOrderId(orderId);
        tbOrderShipping.setUpdated(date);
        tbOrderShipping.setCreated(date);
        i += tbOrderShippingMapper.insertSelective(tbOrderShipping);
        if(i == 2+list.size()){
            return orderId;
        }
        return null;
    }
}
