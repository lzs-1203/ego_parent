package com.ego.controller;

import com.ego.commoms.pojo.EasyUiDataGrid;
import com.ego.commoms.pojo.EgoResult;
import com.ego.pojo.TbContent;
import com.ego.servcie.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/9
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TbContentController {

    @Autowired
    private TbContentService tbContentService;

    @GetMapping("/content/query/list")
    public EasyUiDataGrid showByCategoryid(Long categoryId,int page, int rows){
        return tbContentService.findByCategoryid(categoryId, page, rows);
    }

    @PostMapping("/content/save")
    public EgoResult insert(TbContent tbContent){
        return tbContentService.save(tbContent);
    }

    @PostMapping("/rest/content/edit")
    public EgoResult changeContent(TbContent tbContent){
        return tbContentService.change(tbContent);
    }

    @PostMapping("/content/delete")
    public EgoResult deleteContent(Long[] ids){
        return tbContentService.delete(ids);
    }

}
