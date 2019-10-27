package com.ego.order.interceptor;

import com.ego.commoms.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器： 只拦截控制器
 * @Auther: Constant.Wang
 * @Date: 2019/8/20
 * @Description: com.ego.order.interceptor
 * @version: 1.0
 */
//此注解的作用是将拦截器放进springmvc的容器当中。
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${mylogin.cookie.name}")
    private String loginCookieName; //用户登陆时存放在cookie中的key值
    @Value("${passport.url.showLogin}")
    private String showLogin;
    /**
     *     // 进入控制器之前执行
     *     // 返回值为true表示不拦截，放行
     *     // 返回值为false表示阻止，不允许访问。
     *     在登陆之后才可以访问订单的页面，没有登陆的直接跳转到登陆的页面
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String cookieValue = CookieUtils.getCookieValue(request, loginCookieName);
        if(cookieValue != null && !cookieValue.equals("")){
            if(redisTemplate.hasKey(cookieValue)){
                redisTemplate.setValueSerializer(new StringRedisSerializer());
                Object userJson = redisTemplate.opsForValue().get(cookieValue);
                if(userJson != null && !userJson.toString().equals("")){
                    return true;//只有这一种情况，才是登陆的状态
                }
            }
        }
        response.sendRedirect(showLogin);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
