package com.ego.item.service;

import com.ego.commoms.pojo.SearchSolrResult;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/15
 * @Description: com.ego.item.service
 * @version: 1.0
 */
public interface TbItemService {


    /**
     * 显示商品的详情信息
     * @param id
     * @return
     */
    SearchSolrResult showImage(Long id);

}
