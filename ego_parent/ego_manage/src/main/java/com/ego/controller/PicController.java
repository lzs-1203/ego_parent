package com.ego.controller;

import com.ego.servcie.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class PicController {

    @Autowired
    private PicService picService;

    /*图片的上传*/
    @PostMapping("/pic/upload")
    public Map<String , Object> uploadpic(MultipartFile uploadFile){
        return picService.picupload(uploadFile);
    }
}
