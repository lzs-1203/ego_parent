package com.ego.manage.controller;

import com.ego.manage.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class PicController {
    @Autowired
    private PicService picService;

//    @RequestMapping("/pic/upload")
    @PostMapping("/pic/upload")
//    @RequestMapping(name = "/pic/upload",method = RequestMethod.POST)
    public Map<String,Object> upload(MultipartFile uploadFile){
        return picService.upload(uploadFile);
    }
}
