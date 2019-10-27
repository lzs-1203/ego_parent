package com.ego.servcie;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.servcie
 * @version: 1.0
 */
public interface PicService {

    /**
     * 图片的上传
     * @param fil
     * @return
     */
    Map<String ,Object> picupload(MultipartFile fil);
}
