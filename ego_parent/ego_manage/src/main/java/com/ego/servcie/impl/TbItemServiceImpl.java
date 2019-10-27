package com.ego.servcie.impl;

import com.ego.commoms.utils.HttpClientUtil;
import com.ego.commoms.utils.JsonUtils;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.commoms.pojo.EasyUiDataGrid;
import com.ego.commoms.pojo.EgoResult;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.servcie.TbItemService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/6
 * @Description: com.ego.servcie.impl
 * @version: 1.0
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;
    @Autowired
    private RedisTemplate<String ,Object> template;

    @Value("${myredis.itemkey}")
    private String key;
    @Value("${Httpclient.saveurl}")
    private String saveUrl;
    @Value("${Httpclient.deleteurl}")
    private String deleteUrl;


    //设置全局变量--客户端传入的数组id
    String[] idsArry;

    @Override
    public EasyUiDataGrid findAll(int page, int rows) {
        return tbItemDubboService.selectAll(page,rows);
    }

    @Override
    public EgoResult updateStatus(String[] ids, final Byte status) {
        idsArry = ids;
        int i = tbItemDubboService.updataById(ids, status);
        if (i == 1){
            //redis同步
            for(String id : ids){
                template.delete(key + ":"+ id);
            }
            //加入子线程，提升页面加载的而效率。
            new Thread(){
                @Override
                public void run() {
                    //同步solr
                    if(status == 1){
                        for(String id : idsArry){
                            TbItem item = tbItemDubboService.selectById(Long.parseLong(id));
                            Map<String ,String > map = new HashMap<>();
                            map.put("id",id+"");
                            map.put("item_title",item.getTitle());
                            map.put("item_sell_point",item.getSellPoint());
                            map.put("item_price",Long.toString(item.getPrice()));
                            map.put("item_image",item.getImage());
                            map.put("item_category_name",tbItemCatDubboService.selectById(item.getCid()).getName());
                            map.put("item_desc",tbItemDescDubboService.selectById(item.getId()).getItemDesc());
                            HttpClientUtil.doPost(saveUrl,map);
                        }
                    }else{
                        //List<String> list = Arrays.asList(ids);   //list里面的元素不可以修改。
                        List<String> listids =  new ArrayList<>();
                        for(String id : idsArry){
                            listids.add(id);
                        }
                        String json = JsonUtils.objectToJson(listids);
                        String s = HttpClientUtil.doPostJson(deleteUrl, json);

                    }
                }
            }.start();
            return EgoResult.ok();
        }
        return EgoResult.error("操作失败");
    }

    @Override
    public EgoResult insertItem(TbItem tbItem, String desc , String itemParams) {
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        TbItemParamItem tbItemParam = new TbItemParamItem();
        tbItemParam.setParamData(itemParams);
        long id = tbItemDubboService.insertItem(tbItem, tbItemDesc, tbItemParam);
        if(id != 0){
            /***redis**/
            //此处可以不用同步redis,在查询数据的时候，会判断缓存工具中是否有，没有的话，
            //从数据库中查询到后，放到缓存工具中去。
            /**solr同步**/
            Map<String ,String > map = new HashMap<>();
            map.put("id",id+"");
            map.put("item_title",tbItem.getTitle());
            map.put("item_sell_point",tbItem.getSellPoint());
            map.put("item_price",Long.toString(tbItem.getPrice()));
            map.put("item_image",tbItem.getImage());
            map.put("item_category_name",tbItemCatDubboService.selectById(tbItem.getCid()).getName());
            map.put("item_desc",desc);
            HttpClientUtil.doPost("http://localhost:8083/save",map);
            return EgoResult.ok();
        }
        return EgoResult.error("添加失败");
    }

    @Override
    public EgoResult updateItem(TbItem tbItem, String desc, Long itemParamId, String itemParams) {
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setItemId(tbItem.getId());
        TbItemParamItem paramItem = new TbItemParamItem();
        paramItem.setParamData(itemParams);
        paramItem.setId(itemParamId);
        int i = tbItemDubboService.updateItem(tbItem,tbItemDesc,paramItem);
        if( i == 1){
            //redis同步
            template.delete(key+":"+tbItem.getId());
            //solr同步
            Map<String ,String > map = new HashMap<>();
            map.put("id",tbItem.getId()+"");
            map.put("item_title",tbItem.getTitle());
            map.put("item_sell_point",tbItem.getSellPoint());
            map.put("item_price",Long.toString(tbItem.getPrice()));
            map.put("item_image",tbItem.getImage());
            map.put("item_category_name",tbItemCatDubboService.selectById(tbItem.getCid()).getName());
            map.put("item_desc",desc);
            HttpClientUtil.doPost(saveUrl,map);
            return EgoResult.ok();
        }
        return EgoResult.error("更新失败");
    }
}
