package com.ego.item.service;

import java.util.Map;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/12
 * @Description: com.ego.item.service
 * @version: 1.0
 */
public interface ItemCatService {

    /**
     * 导航菜单的显示，在此处使用递归的方式得到，怎个菜单并以map集合的形式返回
     * @return
     */
    Map<String ,Object> showMenu();

}
