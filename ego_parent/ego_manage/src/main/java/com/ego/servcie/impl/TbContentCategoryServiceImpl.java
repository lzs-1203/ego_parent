package com.ego.servcie.impl;

import com.ego.commoms.EgoException.EgoDaoException;
import com.ego.commoms.pojo.EasyUITree;
import com.ego.commoms.pojo.EgoResult;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.pojo.TbContentCategory;
import com.ego.servcie.TbContentCategoryService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/8
 * @Description: com.ego.servcie.impl
 * @version: 1.0
 */
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboService;

    @Override
    public List<EasyUITree> findAllByPid(Long pid) {
        List<TbContentCategory> list = tbContentCategoryDubboService.selectAllByPid(pid);
        List<EasyUITree> listTree = new ArrayList<>();
        for(TbContentCategory tcc : list){
            EasyUITree easyUITree = new EasyUITree();
            easyUITree.setId(tcc.getId());
            easyUITree.setText(tcc.getName());
            easyUITree.setState(tcc.getIsParent() ? "closed" : "open");
            listTree.add(easyUITree);
        }
        return listTree;
    }

    @Override
    public EgoResult saveContentCategory(Long parentId, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        Long index = null;
        try {
            index = tbContentCategoryDubboService.insertCategory(tbContentCategory);
        } catch (EgoDaoException e) {
            e.printStackTrace();
        }
        if( index != null ){
            tbContentCategory.setId(index);
            return EgoResult.ok(tbContentCategory);
        }
        return EgoResult.error("增加失败");
    }

    @Override
    public EgoResult updateContentCategory(Long id, String name) {
        TbContentCategory tcc = new TbContentCategory();
        tcc.setId(id);
        tcc.setName(name);
        int i = tbContentCategoryDubboService.updateCategroy(tcc);
        if( i == 1 ){
            return EgoResult.ok();
        }
        return EgoResult.error("重命名失败");
    }

    @Override
    public EgoResult deleteContentCategory(Long id) {
        try {
            int deleteCategroy = tbContentCategoryDubboService.deleteCategroy2(id);
            if(deleteCategroy == 1){
                return EgoResult.ok();
            }
        } catch (EgoDaoException e) {
            e.printStackTrace();
        }
        return EgoResult.error("删除失败");
    }
}
