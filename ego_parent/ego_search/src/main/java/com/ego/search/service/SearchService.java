package com.ego.search.service;

import com.ego.search.pojo.SolrEntity;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/14
 * @Description: com.ego.search.service
 * @version: 1.0
 */
public interface SearchService {


    /**
     * 初始化数据
     */
    void init();

    /**
     * 访问8080页面（首页面）进行搜索访问
     * @param q
     * @param page
     * @param rows
     * @return
     */
    Map<String ,Object> search(String q , int page, int rows);


    /**
     * 同步添加solr
     * @param se
     * @return
     */
    int save(SolrEntity se);

    /**
     * 同步删除Solr数据
     * @param ids
     * @return
     */
    int delete(List<String> ids);

}
