package com.ego.dubbo.service;

import com.ego.pojo.TbContent;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/9
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbContentDubboService {

    /**
     * 查询指定类目id的类目内容
     * @param categoryId
     * @return
     */
    List<TbContent> selectByCategoryid(Long categoryId, int page , int rows);


    /**
     * 显示前台页面的大广告
     * @param categoryId
     * @return
     */
    List<TbContent> selectByCategoryid2(Long categoryId);

    /**
     * 查询指定类目id的数据总数
     * @param categoryId
     * @return
     */
    Long countContent(Long categoryId);

    /**
     * 新增内容
     * @param tbContent
     * @return
     */
    int insert(TbContent tbContent);

    /**
     * 修改内容(时间不对)
     * @param tbContent
     * @return
     */
    int update(TbContent tbContent);

    /**
     * 删除内容
     * @param ids
     * @return
     */
    int delete(Long[] ids);

}
