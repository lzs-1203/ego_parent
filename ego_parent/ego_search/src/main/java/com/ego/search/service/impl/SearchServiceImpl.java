package com.ego.search.service.impl;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.commoms.pojo.SearchSolrResult;
import com.ego.search.pojo.SolrEntity;
import com.ego.search.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/14
 * @Description: com.ego.search.service.impl
 * @version: 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Autowired
    private SolrTemplate solr;

    @Value("${search.solr.collection}")
    private String collection;

    @Override
    public void init() {
        List<TbItem> list = tbItemDubboService.selectAll2();
        for(TbItem item : list){
            SolrEntity se = new SolrEntity();
            se.setId(item.getId() + "");
            se.setItem_title(item.getTitle());
            se.setItem_sell_point(item.getSellPoint());
            se.setItem_price(Long.toString(item.getPrice()));
            se.setItem_image(item.getImage());
            se.setItem_category_name(tbItemCatDubboService.selectById(item.getCid()).getName());
            se.setItem_desc(tbItemDescDubboService.selectById(item.getId()).getItemDesc());
            solr.saveBean(collection, se);
        }
        solr.commit(collection);

    }

    @Override
    public Map<String, Object> search(String q, int page, int rows) {
        //创建一个map集合，存放页面所需要的数据
        Map<String , Object> map = new HashMap<>();
        /***查询体，里面设置solr管理界面q部分****/
        //构造方法参数查询时属性名
        Criteria criteria = new Criteria("item_keywords");
        //添加条件
        criteria.is(q);
        /***管理界面中设置高亮部分****/
        //创建高亮查询
        HighlightQuery query = new SimpleHighlightQuery(criteria);
        //创建查询条件选项
        HighlightOptions options = new HighlightOptions();
        //高亮属性（属性列表）
        options.addField("item_title item_sell_point");
        //高亮前缀
        options.setSimplePrefix("<span style='color: red;'>");
        //高亮后缀
        options.setSimplePostfix("</span>");
        //应用高亮条件
        query.setHighlightOptions(options);
        /***分页查询***/
        //查询的起始行
        query.setOffset((long)(rows*(page-1)));
        //每页的条数
        query.setRows(rows);
        //开始执行分页查询
        HighlightPage<SolrEntity> highlightPage = solr.queryForHighlightPage(collection, query, SolrEntity.class);
        /**创建页面显示的数据格式***/
        List<SearchSolrResult> resultList = new ArrayList<SearchSolrResult>();
        //获取Solr中响应的highlighting部分
        List<HighlightEntry<SolrEntity>> listHL = highlightPage.getHighlighted();
        //循环取出每一个对象
        for(HighlightEntry<SolrEntity> hle : listHL){
            //hle相当于每一个对象
            //hle.getEntity相当于获取的是非高亮的部分
            SolrEntity hleEntity = hle.getEntity();
            //创建在页面中EL表达式形式的集合显示数据的实体泛型
            SearchSolrResult ssr = new SearchSolrResult();
            ssr.setId(hleEntity.getId());
            ssr.setPrice(Long.parseLong(hleEntity.getItem_price()));

            String image = hleEntity.getItem_image();
            ssr.setImages(image!=null&&!image.equals("") ? image.split(",") : new String[0]);
            //获取高亮列或者高亮的数据
            List<HighlightEntry.Highlight> listHLH = hle.getHighlights();
            //循环判断是否含有高亮的数据
            for(HighlightEntry.Highlight heh : listHLH){
                //heh.getField 高亮属性
                //heh.getSnipplets()高亮内容，默认是list所有get(0)
                if(heh.getField().getName().equals("item_title")){
                    ssr.setTitle(heh.getSnipplets().get(0));
                }else{
                    ssr.setTitle(hleEntity.getItem_title());
                }
                if(heh.getField().getName().equals("item_sell_point")){
                    ssr.setSellPoint(heh.getSnipplets().get(0));
                }else{
                    ssr.setSellPoint(hleEntity.getItem_sell_point());
                }
            }
            resultList.add(ssr);
        }
        map.put("itemList",resultList);
        map.put("totalPages",highlightPage.getTotalPages());
        map.put("page",page);
        map.put("query",q);
        return map;
    }

    @Override
    public int save(SolrEntity se) {
        //有两种方式，来进行对solr的同步。
        UpdateResponse ur = solr.saveBean(collection, se);
        solr.commit(collection);
        if(ur.getStatus() == 0){
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(List<String> ids) {
        UpdateResponse ur = solr.deleteByIds(collection, ids);
        solr.commit(collection);
        if(ur.getStatus() == 0){
            return 1;
        }
        return 0;
    }
}
