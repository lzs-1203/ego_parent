package com.ego.item.service;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/15
 * @Description: com.ego.item.service
 * @version: 1.0
 */
public interface TbItemParamItemServcie {

    /**
     * 显示商品的参数规格，在前台页面直接将数据放在div中，在服务端需要随数据进行封装
     * @param itemId
     * @return
     */
    String showParam(Long itemId);

}
