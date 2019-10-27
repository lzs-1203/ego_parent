package com.ego.dubbo.service;


import com.ego.commoms.EgoException.EgoDaoException;
import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/8
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbContentCategoryDubboService {


    /**
     * 显示所有的
     * @param pid
     * @return
     */
    List<TbContentCategory> selectAllByPid(Long pid);

    /**
     * 增加内容种类
     * @param tcc
     * @return
     */
    Long insertCategory(TbContentCategory tcc) throws EgoDaoException;


    /**
     * 重命名(如果新名称已经和处于正常情况下的其他分类同名，直接修改失败)
     * @param tcc
     * @return
     */
    int updateCategroy(TbContentCategory tcc);

    /**
     * 删除操作--真正的删除
     * 1.判断要删除的节点为父节点，若为父节点，则禁止删除
     * 2.判断要删除的节点的父节点有超过一个字节点的数目，若只有一个子节点，则修改父节点的状态为false
     * @param id
     * @return
     */
    int deleteCategroy(Long id) throws EgoDaoException;


    /**
     * 删除操作---伪删除
     * 1.判断要删除的节点的父节点下是否还有子节点，没有子节点，则需要更改父节节点的状态
     * 2.修改本节点下的所有子节点为删除状态，---伪删除
     * @param id
     * @return
     */
    int deleteCategroy2(Long id) throws EgoDaoException;




}
