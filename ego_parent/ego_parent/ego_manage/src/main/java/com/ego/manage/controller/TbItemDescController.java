package com.ego.manage.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TbItemDescController {
    @Autowired
    private TbItemDescService tbItemDescService;

    @GetMapping("/rest/item/query/item/desc/{id}")
    public EgoResult selectById(@PathVariable Long id){
        return tbItemDescService.selectById(id);
    }
}
