package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/16
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbUserDubboService {

    TbUser selectByUser(TbUser tbUser);

    TbUser selectByUsernamePhone(String username, String phone);

    int insert(TbUser tbUser);



}
