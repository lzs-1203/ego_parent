package com.ego.search.controller;

import com.ego.search.pojo.SolrEntity;
import com.ego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/14
 * @Description: com.ego.search.controller
 * @version: 1.0
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/init")
    @ResponseBody
    public String init(){
        long start = System.currentTimeMillis();
        searchService.init();
        long end = System.currentTimeMillis();
        long count = end - start;
        return "耗时："+(count/1000)+"秒";
    }

    //伪静态化，提高搜索引擎的效率
    @RequestMapping("/search.html")
    public String showItem(String q,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "12") int rows, Model model){
        model.addAllAttributes(searchService.search(q, page, rows));
        return "search";
    }


    @RequestMapping("/save")
    @ResponseBody   //为防止错误类型转换，这里需要加上一个@ResponseBody注解
    public int save(SolrEntity se){
        return searchService.save(se);
    }

    //@RequestBody把请求体中inputstream流转换为特定对象。
    //常用的情况为与请求时content-type设置为application/json的情况。
    @RequestMapping("/delete")
    @ResponseBody
    public int delete(@RequestBody List<String> ids){
        return searchService.delete(ids);
    }

}
