package com.ego.servcie.impl;

import com.ego.commoms.utils.FtpUtil;
import com.ego.servcie.PicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.servcie.impl
 * @version: 1.0
 */
@Service
public class PicServiceImpl implements PicService {

    @Value("${vsftpd.host}")
    private String host;
    @Value("${vsftpd.port}")
    private int port;
    @Value("${vsftpd.username}")
    private String username;
    @Value("${vsftpd.password}")
    private String password;
    @Value("${vsftpd.basePath}")
    private String basePath;
    @Value("${vsftpd.filePath}")
    private String filePath;

    @Override
    public Map<String, Object> picupload(MultipartFile fil) {
        Map<String, Object> map = new HashMap<>();
        String fileName = fil.getOriginalFilename();
        //重新给文件命名，并且文件名通过UUID的方式来取，避免图片上传因为名称相同导致覆盖
        fileName = UUID.randomUUID() + fileName.substring(fileName.indexOf("."));

        try {
            boolean uploadFile = FtpUtil.uploadFile(host, port, username, password,
                    basePath, filePath, fileName, fil.getInputStream());
            if(uploadFile){
                map.put("error",0);
                map.put("url","http://"+host+"/"+fileName);
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("error",1);
        map.put("message","上传图片失败");
        return map;
    }
}




