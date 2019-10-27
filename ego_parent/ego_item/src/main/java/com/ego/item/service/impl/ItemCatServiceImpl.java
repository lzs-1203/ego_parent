package com.ego.item.service.impl;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.item.pojo.PortalCatMenu;
import com.ego.item.service.ItemCatService;
import com.ego.pojo.TbItemCat;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/12
 * @Description: com.ego.item.service.impl
 * @version: 1.0
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    @Override
    public Map<String, Object> showMenu() {
        Map<String , Object> map = new HashMap<>();
        map.put("data", getMenu(tbItemCatDubboService.selectBypid(0L)));
        return map;
    }

    private List<Object> getMenu(List<TbItemCat> list) {
        List<Object> listResult = new ArrayList<>();
        for(TbItemCat cat : list) {
            if(cat.getIsParent()){
                PortalCatMenu ptc = new PortalCatMenu();
                ptc.setU("/products/"+cat.getId()+".html");
                ptc.setN("<a href='/products/"+cat.getId()+".html'>"+cat.getName()+"</a>");
                ptc.setI(getMenu(tbItemCatDubboService.selectBypid(cat.getId())));
                listResult.add(ptc);
            }else{
                listResult.add("/products/"+cat.getId()+".html|"+cat.getName());
            }
        }
        return listResult;
    }
}
