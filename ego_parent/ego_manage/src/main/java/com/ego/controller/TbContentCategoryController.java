package com.ego.controller;

import com.ego.commoms.pojo.EasyUITree;
import com.ego.commoms.pojo.EgoResult;
import com.ego.servcie.TbContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/8
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TbContentCategoryController {

    @Autowired
    private TbContentCategoryService tbContentCategoryService;

    @GetMapping("/content/category/list")
    public List<EasyUITree> showContentCategoryTree(@RequestParam(defaultValue = "0") Long id){
        return tbContentCategoryService.findAllByPid(id);
    }

    @PostMapping("/content/category/create")
    public EgoResult saveContentCategory(Long parentId, String name){
        return tbContentCategoryService.saveContentCategory(parentId, name);
    }

    @PostMapping("/content/category/update")
    public EgoResult updateContentCategory( Long id, String name){
        return tbContentCategoryService.updateContentCategory(id, name);
    }

    @PostMapping("/content/category/delete/")
    public EgoResult deleteContentCategory(Long id ){
        return tbContentCategoryService.deleteContentCategory(id);
    }


}
