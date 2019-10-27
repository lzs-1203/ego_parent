package com.ego.passport.service;

import com.ego.commoms.pojo.EgoResult;
import com.ego.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/16
 * @Description: com.ego.passport.service
 * @version: 1.0
 */
public interface TbUserService {

    EgoResult userLogin(TbUser tbUser ,HttpServletRequest request , HttpServletResponse response);

    EgoResult getUserbByToken(String token);

    EgoResult userLogout(String token , HttpServletRequest request, HttpServletResponse response);

    EgoResult usernamecheck(String username);

    EgoResult phonecheck(String phone);

    EgoResult saveUser(TbUser tbUser);

}
