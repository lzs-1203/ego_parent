package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/9
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbContentDubboServiceImpl implements TbContentDubboService {

    @Autowired
    private TbContentMapper tbContentMapper;

    //分页查询全部，分页显示
    @Override
    public List<TbContent> selectByCategoryid(Long categoryId, int page, int rows) {
        PageHelper.startPage(page,rows);
        TbContentExample example = new TbContentExample();
        if(categoryId != 0){
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbContent> pi = new PageInfo<>(list);
        return pi.getList();
    }

    //分页查询全部，返回集合
    @Override
    public List<TbContent> selectByCategoryid2(Long categoryId) {
        TbContentExample example = new TbContentExample();
        if(categoryId != 0){
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbContent> pi = new PageInfo<>(list);
        return pi.getList();
    }

    @Override
    public Long countContent(Long categoryId) {
        TbContentExample example = new TbContentExample();
        if(categoryId != 0){
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        return tbContentMapper.countByExample(example);
    }

    @Override
    public int insert(TbContent tbContent) {
        Date date = new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        return tbContentMapper.insertSelective(tbContent);
    }

    @Override
    public int update(TbContent tbContent) {
        tbContent.setUpdated(new Date());
        return tbContentMapper.updateByPrimaryKeySelective(tbContent);
    }

    @Override
    public int delete(Long[] ids) {
        int i = 0;
        for(Long id : ids){
            i += tbContentMapper.deleteByPrimaryKey(id);
        }
        if( i ==  ids.length ){
            return 1;
        }
        return 0;
    }


}
