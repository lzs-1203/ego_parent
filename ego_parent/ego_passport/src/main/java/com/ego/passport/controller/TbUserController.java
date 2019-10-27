package com.ego.passport.controller;

import com.ego.commoms.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/16
 * @Description: com.ego.passport.controller
 * @version: 1.0
 */
@Controller
public class TbUserController {

    @Autowired
    private TbUserService tbUserService;

    @RequestMapping("/user/showLogin")
    public String showLogin(Model model, @RequestHeader(name = "Referer") String referer){
        if(referer != null && !referer.equals("http://localhost:8084/user/showRegister")){
            model.addAttribute("redirect",referer);
        }
        return "login";
    }

    @RequestMapping("/user/showRegister")
    public String showRegister(){
        return "register";
    }

    @PostMapping("/user/login")
    @ResponseBody
    public EgoResult userLogin(TbUser tbUser ,HttpServletRequest request , HttpServletResponse response){
        return tbUserService.userLogin(tbUser , request, response);
    }


    @RequestMapping("/user/token/{token}")
    @ResponseBody
    @CrossOrigin
    public EgoResult getUser(@PathVariable String token){
        return tbUserService.getUserbByToken(token);
    }

    @RequestMapping("/user/logout/{token}")
    @ResponseBody
    @CrossOrigin
    public EgoResult logout(@PathVariable String token, HttpServletRequest request , HttpServletResponse response){
        return tbUserService.userLogout(token,request,response);
    }

    @RequestMapping("/user/check/{username}/1")
    @ResponseBody
    public EgoResult usernamecheck(@PathVariable String username){
        return tbUserService.usernamecheck(username);
    }

    @RequestMapping("/user/check/{phone}/2")
    @ResponseBody
    public EgoResult phonecheck(@PathVariable String phone){
        return tbUserService.phonecheck(phone);
    }

    @RequestMapping("/user/register")
    @ResponseBody
    public EgoResult saveUser(TbUser tbUser){
        return tbUserService.saveUser(tbUser);
    }
}
