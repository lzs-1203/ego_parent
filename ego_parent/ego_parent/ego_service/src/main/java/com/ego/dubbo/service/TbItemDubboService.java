package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;

import java.util.List;

public interface TbItemDubboService {
    /**
     * 分页查询
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<TbItem> selectByPage(int pageNumber,int pageSize);

    /**
     * 批量更新，商品状态
     * @param status
     * @param ids
     * @return
     */
    int updateStatusByIds(Byte status,Long[] ids) throws DaoException;

    /**
     * 总条数
     * @return
     */
    Long selectCount();

    /**
     * 新增
     * @param item 商品信息
     * @param desc 描述信息
     * @return 如果为1表示成功。
     */
    int insert(TbItem item, TbItemDesc desc) throws DaoException;

    /**
     * 修改。
     * @param item
     * @param desc
     * @return
     * @throws DaoException
     */
    int update(TbItem item,TbItemDesc desc) throws  DaoException;
}
