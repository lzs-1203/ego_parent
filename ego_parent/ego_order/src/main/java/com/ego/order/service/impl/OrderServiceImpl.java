package com.ego.order.service.impl;

import com.ego.commoms.pojo.CartEntity;
import com.ego.commoms.utils.CookieUtils;
import com.ego.commoms.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.OrderEntity;
import com.ego.order.pojo.OrderParam;
import com.ego.order.service.OrderService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbUser;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/20
 * @Description: com.ego.order.service.impl
 * @version: 1.0
 * 此项目使用拦截器，对整个控制器进行拦截，进行登陆不能访问的限制
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RedisTemplate<String , Object> redisTemplate;

    @Value("${mylogin.cookie.name}")
    private String loginCookieName; //用户登陆时存放在cookie中的key值

    @Value("${cart.usercart}")
    private String userCart;

    @Reference
    private TbItemDubboService tbItemDubboService;

    @Reference
    private TbOrderDubboService tbOrderDubboService;

    /**
     * 生成订单
     * @param ids
     * @param req
     * @return
     */
    @Override
    public List<OrderEntity> selectList(List<Long> ids, HttpServletRequest req ) {
        List<OrderEntity> orderList = new ArrayList<>();
        //得到登陆用户的信息
        String cookieValue = CookieUtils.getCookieValue(req, loginCookieName);
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        String json = redisTemplate.opsForValue().get(cookieValue).toString();
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        //取出用户购物车中的商品信息
        String userCartKey = userCart + ":" + user.getId();
        String jsonUserList = redisTemplate.opsForValue().get(userCartKey).toString();
        List<OrderEntity> userCartList = JsonUtils.jsonToList(jsonUserList, OrderEntity.class);
        for(Long id : ids){
            for(OrderEntity oe : userCartList){
                //判断购物车中的商品，和要结算的商品
                if(oe.getId().equals(id + "")){
                    //将需要结算的商品数量和库存进行比较--首先需要在数据库中进行查询（为保险起见）
                    TbItem item = tbItemDubboService.selectById(id);
                    if((int)item.getNum() >= oe.getNum()){
                        oe.setEnough(true);
                    }else{
                        oe.setEnough(false);
                    }
                    orderList.add(oe);
                    break;
                }
            }
        }
        return orderList;
    }




    @Override
    public Map<String, Object> create(OrderParam orderParam, HttpServletRequest request) {
        Map<String , Object> map = new HashMap<>();
        TbOrder tbOrder = new TbOrder();
        tbOrder.setPayment(orderParam.getPayment());
        tbOrder.setPaymentType(orderParam.getPaymentType());
        String orderId = tbOrderDubboService.insert(tbOrder, orderParam.getOrderItems(), orderParam.getOrderShipping());
        if(orderId != null && !orderId.equals("")){
            map.put("orderId", orderId);
            map.put("payment", orderParam.getPayment());
            //对时间进行判断，得到送达的时间
            Calendar c = Calendar.getInstance();
            Calendar c11 = Calendar.getInstance();
            Calendar currTime = (Calendar) c11.clone();
            Calendar c21 = (Calendar) c11.clone();
            c11.set(Calendar.HOUR_OF_DAY,11);
            c11.set(Calendar.MINUTE,0);
            c11.set(Calendar.SECOND,0);

            c21.set(Calendar.HOUR_OF_DAY,21);
            c21.set(Calendar.MINUTE,0);
            c21.set(Calendar.SECOND,0);
            if(currTime.compareTo(c11) <= 0){//下单时间小于指定的时间
                //当天能到
            }else if (currTime.compareTo(c11) == 1 && currTime.compareTo(c21) <= 0){
                //第二天上午可以到
                currTime.add(Calendar.DAY_OF_MONTH, 1);
            }else{
                //第二天下午可以到
                currTime.add(Calendar.DAY_OF_MONTH, 1);
            }
            map.put("date", currTime.getTime());  //设定上午11点之前下单当天送到，晚上9点之前第二天送到。
            /***当将订单提交后，将对应的数据从redis中删除**/
            //取出购物车中的商品的信息，删除，放入redis中。
            String cookieValue = CookieUtils.getCookieValue(request, loginCookieName);
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            String json = redisTemplate.opsForValue().get(cookieValue).toString();
            TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
            //取出用户购物车中的商品信息
            String userCartKey = userCart + ":" + user.getId();
            String jsonUserList = redisTemplate.opsForValue().get(userCartKey).toString();
            List<CartEntity> userCartList = JsonUtils.jsonToList(jsonUserList, CartEntity.class);
            for(TbOrderItem toi : orderParam.getOrderItems()){
                for(CartEntity ce : userCartList){
                    System.out.println(toi.getId());//?????为什么是个空？？
                    if(toi.getItemId().equals(ce.getId())){
                        userCartList.remove(ce);
                        break;
                    }
                }
            }
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<List>(List.class));
            redisTemplate.opsForValue().set(userCartKey,userCartList);
            return map;
        }
        return null;
    }


}
