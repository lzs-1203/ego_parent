package com.ego.dubbo.service;

import com.ego.commoms.EgoException.ItemUnderStockException;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/20
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */

public interface TbOrderDubboService {

    /**
     * @param tbOrder
     * @param list
     * @param tbOrderShipping
     * @return 订单id，为null表示订单创建失败
     * @throws ItemUnderStockException
     */
    String insert(TbOrder tbOrder, List<TbOrderItem> list, TbOrderShipping tbOrderShipping) throws ItemUnderStockException;
}
