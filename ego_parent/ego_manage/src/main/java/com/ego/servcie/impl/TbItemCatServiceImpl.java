package com.ego.servcie.impl;

import com.ego.commoms.pojo.EasyUITree;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.pojo.TbItemCat;
import com.ego.servcie.TbItemCatService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.servcie.impl
 * @version: 1.0
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {

    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    @Override
    public List<EasyUITree> showItemCat(Long id) {
        List<TbItemCat> list = tbItemCatDubboService.selectBypid(id);
        List<EasyUITree> treeList = new ArrayList<>();
        for(TbItemCat itemCat : list){
            EasyUITree tree = new EasyUITree();
            tree.setId(itemCat.getId());
            tree.setText(itemCat.getName());
            tree.setState(itemCat.getIsParent() ? "closed" : "open");
            treeList.add(tree);
        }
        return treeList;
    }
}
