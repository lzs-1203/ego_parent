package com.ego.passport.service.impl;

import com.ego.commoms.pojo.EgoResult;
import com.ego.commoms.utils.CookieUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/16
 * @Description: com.ego.passport.service.impl
 * @version: 1.0
 */
@Service
public class TbUserServiceImpl implements TbUserService {

    @Reference
    private TbUserDubboService tbUserDubboService;

    @Autowired
    private RedisTemplate<String, Object> template;

    @Override
    public EgoResult userLogin(TbUser tbUser, HttpServletRequest request , HttpServletResponse response) {
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        TbUser user = tbUserDubboService.selectByUser(tbUser);
        if(user != null){
            //创建cookie
            String token = UUID.randomUUID().toString();
            CookieUtils.setCookie(request,response,"TT_TOKEN",token);
            //将用户的信息保存到redis中，模拟session单体项目session作用域的作用
            //首先将密码置空，敏感的数据一般不放在这里面
            user.setPassword("");
            template.opsForValue().set(token,user);
            return EgoResult.ok();
        }
        return EgoResult.error("用户名或密码错误");
    }

    @Override
    public EgoResult getUserbByToken(String token) {
        template.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));
        if(template.hasKey(token)){
            TbUser user = (TbUser) template.opsForValue().get(token);
            return EgoResult.ok(user);
        }
        return EgoResult.error("获取用户信息失败");
    }

    //退出
    @Override
    public EgoResult userLogout(String token, HttpServletRequest request, HttpServletResponse response) {
        //cookie在这里的删除是不生效的，在这里是跨域请求，不在同一个项目中
        //解决方式是，将退出登陆写成同步的操作。
        CookieUtils.deleteCookie(request, response, token);
        template.delete(token);
        return EgoResult.ok();
    }

    @Override
    public EgoResult usernamecheck(String username) {
        TbUser user = tbUserDubboService.selectByUsernamePhone(username, null);
        if(user != null){
            return EgoResult.error("该用户名已经存在");
        }
        return EgoResult.ok("该用户名不存在，可以注册！");
    }

    @Override
    public EgoResult phonecheck(String phone) {
        TbUser user = tbUserDubboService.selectByUsernamePhone(null, phone);
        if(user != null){
            return EgoResult.error("该手机号已经注册");
        }
        return EgoResult.ok("该手机号不存在，可以注册！");
    }

    @Override
    public EgoResult saveUser(TbUser tbUser) {
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        int insert = tbUserDubboService.insert(tbUser);
        if(insert == 1){
            return EgoResult.ok();
        }
        return EgoResult.error("注册失败！");
    }


}
