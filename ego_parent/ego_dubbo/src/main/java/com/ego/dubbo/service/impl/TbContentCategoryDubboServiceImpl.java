package com.ego.dubbo.service.impl;

import com.ego.commoms.EgoException.EgoDaoException;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/8
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<TbContentCategory> selectAllByPid(Long pid) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        //排序
        example.setOrderByClause("sort_order ASC");
        //添加两个条件,通过pid和此时分类的一个状态
        example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
        return tbContentCategoryMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public Long insertCategory(TbContentCategory tbContentCategory) throws EgoDaoException {
        Date date = new Date();
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(date);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setStatus(1);
        //添加一条
        int i = tbContentCategoryMapper.insertSelectiveReturnId(tbContentCategory);
        System.out.println("TbContentCategoryDubboServiceImpl.insertCategory :"+tbContentCategory);
        //判断新增是否成功
        if( i == 1){
            TbContentCategory parentTcc = tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
            //判断父分类是否已经是父节点
            if(!parentTcc.getIsParent()){
                TbContentCategory updataeParent = new TbContentCategory();
                updataeParent.setId(parentTcc.getId());
                updataeParent.setIsParent(true);
                updataeParent.setUpdated(date);
                int result = tbContentCategoryMapper.updateByPrimaryKeySelective(updataeParent);
                if( result == 0 ){
                    throw new EgoDaoException("添加目录 - 修改父类失败");
                }
            }
            //判断本节点的id是否为空，也就是在新增的时候，子标签没有查询到更新的主键id
            if(tbContentCategory.getId() != null){
                //返回本节点的id，因为这里provider和consumer之间是通过dubbo协议进行的数据传输，
                //在两个服务器上，此时的类目不同的服务其上面对象为两个不同引用的对象。
                return tbContentCategory.getId();
            }
        }
        //添加后，查询父节点是否已经为父节点，若为父节点，则不做修改，若不为父节点则进行修改。
        return 0L;
    }

    @Override
    public int updateCategroy(TbContentCategory tcc) {
        //通过名称进行查询，看是否有同名的类目，有同名的类目则直接修改失败
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andNameEqualTo(tcc.getName()).andStatusEqualTo(1);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            return 0;   //此时有同类目的，在这里就许哟啊返回一个0；
        }
        tcc.setUpdated(new Date());//修改更新的时间
        return tbContentCategoryMapper.updateByPrimaryKeySelective(tcc);
    }


    /**
     * 删除操作
     * 1.判断要删除的节点是否为父节点，若为父节点，则禁止删除
     * 2.判断要删除的节点的父节点有超过一个字节点的数目，若只有一个子节点，则修改父节点的状态为false
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int deleteCategroy(Long id)  throws EgoDaoException{
        TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);//查询本节点的父节点
        Long parentid = category.getParentId();
        //判断要删除的节点是否为父节点，若为父节点，则禁止删除
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        if( list != null && list.size() >0){  //表式此节点为父节点
            return 0;
        }
        //判断要删除的节点的父节点有超过一个字节点的数目，
        // 若只有一个子节点，则修改父节点的状态为false
        //通过查询得到此节点的父节点id
        TbContentCategoryExample example2 = new TbContentCategoryExample();
        example2.createCriteria().andParentIdEqualTo(parentid);
        List<TbContentCategory> list2 = tbContentCategoryMapper.selectByExample(example2);
        if( list2 != null && list2.size() > 1 ){
            int i = tbContentCategoryMapper.deleteByPrimaryKey(id);
            if( i == 1){
                return 1;
            }
            return 0;
        }
        //此节点为叶子节点
        int index = tbContentCategoryMapper.deleteByPrimaryKey(id);
        TbContentCategory tcc = new TbContentCategory();
        tcc.setIsParent(false);
        tcc.setId(parentid);
        index += tbContentCategoryMapper.updateByPrimaryKeySelective(tcc);
        if(index == 2){
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public int deleteCategroy2(Long id) throws EgoDaoException{
        Date date = new Date();
        //修改本节点的状态
        TbContentCategory tcc = new TbContentCategory();
        tcc.setId(id);
        tcc.setStatus(2);
        tcc.setUpdated(date);
        int updateTcc = tbContentCategoryMapper.updateByPrimaryKeySelective(tcc);
        if(updateTcc ==1){
            //查询父节点下是否还有子节点
            TbContentCategory currNode = tbContentCategoryMapper.selectByPrimaryKey(id);
            TbContentCategoryExample example = new TbContentCategoryExample();
            example.createCriteria().andParentIdEqualTo(currNode.getParentId()).andStatusEqualTo(1);
            long count = tbContentCategoryMapper.countByExample(example);
            if(count == 0 ){
                TbContentCategory parentNode = new TbContentCategory();
                parentNode.setId(currNode.getParentId());
                parentNode.setIsParent(false);
                parentNode.setUpdated(date);
                int i = tbContentCategoryMapper.updateByPrimaryKeySelective(parentNode);
                if( i == 0 ){
                    throw new EgoDaoException("删除目录--修改父节点状态失败");
                }
            }
            //修改本节点的字节点状态全部为删除的状态---使用递归的写法
            updateChildNode(tcc);
            return 1;
        }
        return 0;
    }

    //使用递归的方式，来进行子节点的状态修改
    private void updateChildNode(TbContentCategory tcc) {
        int i = tbContentCategoryMapper.updateByPrimaryKeySelective(tcc);   //此处可以看成是递归的开始。
        if( i == 1 ){
            //查询本节点下的所有子节点
            TbContentCategoryExample childNodeExample = new TbContentCategoryExample();
            childNodeExample.createCriteria().andParentIdEqualTo(tcc.getId());
            List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(childNodeExample);
            for(TbContentCategory childTcc : list){//当子节点修改全部完成后，递归结束。
                TbContentCategory childNode = new TbContentCategory();
                childNode.setId(childTcc.getId());
                childNode.setStatus(2);
                childNode.setUpdated(tcc.getUpdated());
                int childUpdate = tbContentCategoryMapper.updateByPrimaryKeySelective(childNode);
                updateChildNode(childNode);
            }
        }else{
            throw new EgoDaoException("删除目录--修改子节点的内容失败");
        }
    }
}
