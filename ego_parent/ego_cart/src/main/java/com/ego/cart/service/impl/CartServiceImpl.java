package com.ego.cart.service.impl;

import com.ego.commoms.pojo.CartEntity;
import com.ego.cart.service.CartService;
import com.ego.commoms.pojo.EgoResult;
import com.ego.commoms.pojo.SearchSolrResult;
import com.ego.commoms.utils.CookieUtils;
import com.ego.commoms.utils.HttpClientUtil;
import com.ego.commoms.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/19
 * @Description: com.ego.cart.service.impl
 * @version: 1.0
 */
@Service
public class CartServiceImpl implements CartService {

    @Value("${myredis.itemkey}")
    private String itemKey; //一般情况下，是此名称加上id

    @Value("${mylogin.cookie.name}")
    private String loginCookieName; //用户登陆时存放在cookie中的key值

    @Value("${passport.url.getUserInfo}")
    private String userInfo;

    @Value("${cart.tempcart}")
    private String tempCart;

    @Value("${cart.usercart}")
    private String userCart;

    @Autowired
    private RedisTemplate<String , Object> redisTemplate;

    @Reference
    private TbItemDubboService tbItemDubboService;



    @Override
    public boolean addCart(Long id, int num, HttpServletRequest req, HttpServletResponse resp) {
        String key = itemKey + ":" + id;
        SearchSolrResult redisItem = getRedisItem(id, key); //当前要添加购物车的商品信息
        //判断用户是否登陆--先从cookie中进行查询--再通过去redis中查询
        String cookieValue = CookieUtils.getCookieValue(req, loginCookieName);
        /**用户登陆后的购物车添加**/
        if(cookieValue != null && !cookieValue.equals("")) { //cookie有登陆标志。
            String s = HttpClientUtil.doPost(userInfo + cookieValue);
            EgoResult egoResult = JsonUtils.jsonToPojo(s, EgoResult.class);
            if (egoResult.getStatus() == 200) { //用户已经登陆
                String userCartKey = userCart + ":" + ((LinkedHashMap)egoResult.getData()).get("id");
                //判断是否有临时购物车
                String tempValue = CookieUtils.getCookieValue(req, tempCart,true);
                List<CartEntity> tempList = null;
                if(tempValue != null && !tempValue.equals("")){
                    tempList = JsonUtils.jsonToList(tempValue, CartEntity.class);
                }
                //判断是否有用户购物车
                List<CartEntity> userCartList = null;
                if(!redisTemplate.hasKey(userCartKey)){    //没有用户购物车
                    userCartList = new ArrayList<>();
                    CartEntity ce = new CartEntity();
                    BeanUtils.copyProperties(redisItem, ce);
                    ce.setNum(num);
                    userCartList.add(ce);
                    //判断是否有临时的购物车,并合并两个购物车。
                    if(tempList != null && tempList.size() > 0) {
                        userCartList = isExistTempCart(tempList, userCartList);
                    }
                    //保存用户购物车在redis中
                    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<List>(List.class));
                    redisTemplate.opsForValue().set(userCartKey, userCartList);
                }else{
                    //有用户购物车
                    redisTemplate.setValueSerializer(new StringRedisSerializer());
                    String json = redisTemplate.opsForValue().get(userCartKey).toString();
                    userCartList= JsonUtils.jsonToList(json, CartEntity.class);

                    //判断购物车辆中是否有要添加的商品
                    userCartList = isExistItem(id, userCartList, num, redisItem);
                    //判断临时购物车的存在,并合并两个购物车。
                    if(tempList != null && tempList.size() > 0) {
                        userCartList = isExistTempCart(tempList, userCartList);
                    }
                    //保存用户购物车在redis中
                    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<List>(List.class));
                    redisTemplate.opsForValue().set(userCartKey, userCartList);
                }
                //清除cookie中的临时购物车的信息
                CookieUtils.deleteCookie(req, resp, tempCart);
                return true;
            }
        }
        /**用户没有登陆-- 用户没有登陆，现在添加购物车，
         就是在cookie中新建一个临时的购物车，将添加的信息存放在临时的购物车中
         */
        //判断是否有临时的购物车
        String tempCookieValue = CookieUtils.getCookieValue(req, tempCart,true);
        List<CartEntity> cartEntityList = null;
        if(tempCookieValue != null && !tempCookieValue.equals("")){ //有临时的购物车
            cartEntityList = JsonUtils.jsonToList(tempCookieValue, CartEntity.class);
            //判断临时购物车中的商品和要添加的商品是否相同，相同则增加数目，不同则直接添到集合中去
            cartEntityList = isExistItem(id, cartEntityList, num, redisItem);
        }else{//没有临时购物车
            cartEntityList = new ArrayList<>();
            CartEntity ce = new CartEntity();
            BeanUtils.copyProperties(redisItem, ce);
            ce.setNum(num);
            cartEntityList.add(ce);
        }
        //得到临时的购物车的数据后，将数据保存在cookie中。
        if(cartEntityList != null && cartEntityList.size() > 0){
            String json = JsonUtils.objectToJson(cartEntityList);
            CookieUtils.setCookie(req,resp,tempCart,json,true);
        }
        return true;
    }

    @Override
    public List<CartEntity> getCartProduction(HttpServletRequest req, HttpServletResponse resp) {
        //判断用户是否登陆
        //用户登陆从redis中获取
        //用户没有登陆，从cookie中获取
        String cookieValue = CookieUtils.getCookieValue(req, loginCookieName);
        if(cookieValue != null && !cookieValue.equals("")){
            String s = HttpClientUtil.doPost(userInfo+cookieValue);
            //从这里转换过来的数据为，LinkedHashMap类型的数据
            EgoResult egoResult = JsonUtils.jsonToPojo(s, EgoResult.class);
            if( egoResult.getStatus() == 200){      //说明客户已经登陆
                //拼接用户购物车的key值，在redis中存储。使用key值可以获取value值
                String userCartKey = userCart + ":" + ((LinkedHashMap)egoResult.getData()).get("id");
                //判断是否有用户购物车
                redisTemplate.setValueSerializer(new StringRedisSerializer());
                List<CartEntity> list = null;
                if(redisTemplate.hasKey(userCartKey)){
                    String json = (String) redisTemplate.opsForValue().get(userCartKey);
                    //得到用户购物车
                    list = JsonUtils.jsonToList(json, CartEntity.class);
                }else{
                    //没有用户的购物车，新创建一个用户的购物车。
                    new ArrayList<>();
                }
                //合并临时的购物车和用户的购物车
                //获取临时购物车
                String tempCartListString = CookieUtils.getCookieValue(req, tempCart);
                if(tempCartListString != null && !tempCartListString.equals("")){
                    List<CartEntity> tempList = JsonUtils.jsonToList(tempCartListString, CartEntity.class);
                    //循环遍历，查找看是否有相同的商品
                    //有相同的商品则， 加一
                    //么有相同的商品则，直接将临时购物车里面的信息，存放在用户的购物车中
                    list = isExistTempCart(tempList, list);
                    CookieUtils.deleteCookie(req,resp, tempCart);
                }
                return list;
            }
        }
        //客户没有登陆，直接从cookie中获取数据
        String tempCartListString = CookieUtils.getCookieValue(req, tempCart, true);
        if(tempCartListString != null && !tempCartListString.equals("")){
            List<CartEntity> tempCartList = JsonUtils.jsonToList(tempCartListString, CartEntity.class);
            return tempCartList;
        }
        return new ArrayList<>();
    }



    /**
     * 从缓存中取出需要添加到购物车中的商品的信息
     * @param id
     * @return
     */
    private SearchSolrResult getRedisItem(Long id, String key) {
        SearchSolrResult ssr = null;
        SearchSolrResult searchSolrResult = null;
        if(!redisTemplate.hasKey(key)){ //如果缓存工具中不存在，直接从数据库中获取
            TbItem item = tbItemDubboService.selectById(id);
            ssr = new SearchSolrResult();
            ssr.setId(id+"");
            ssr.setSellPoint(item.getSellPoint());
            ssr.setTitle(item.getTitle());
            ssr.setPrice(item.getPrice());
            String image = item.getImage();
            ssr.setImages(image != null && !image.equals("") ? image.split(",") : new String[0]);
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<SearchSolrResult>(SearchSolrResult.class));
            redisTemplate.opsForValue().set(key,ssr);
        }
        if(ssr == null){
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<SearchSolrResult>(SearchSolrResult.class));
            searchSolrResult = (SearchSolrResult) redisTemplate.opsForValue().get(key);
        }
        return searchSolrResult;
    }


    /***
     * 判断在购物车中是否存在商品
     * @param id
     * @param list
     * @param num
     * @param redisItem
     * @return
     */
    private List<CartEntity> isExistItem(Long id, List<CartEntity> list, int num, SearchSolrResult redisItem) {
        //判断用户购物车中是否有该商品
        boolean isHasElement = false;
        for(CartEntity ce : list){
            if(ce.getId().equals(id+"")){
                ce.setNum(ce.getNum()+num); //数量加一
                isHasElement = true;
                break;
            }
        }
        //么有该商品
        if(!isHasElement){
            CartEntity ce = new CartEntity();
            BeanUtils.copyProperties(redisItem,ce);
            ce.setNum(num);
            list.add(ce);
        }
        return list;
    }


    /**
     * 合并临时购物车和用户购物车
     * @param tempList
     * @param userCartList
     * @return
     */
    private List<CartEntity> isExistTempCart(List<CartEntity> tempList, List<CartEntity> userCartList) {
            //合并临时购物车和用户购物车
            for(CartEntity tempce : tempList){
                boolean isExistElement = false;
                for(CartEntity userce : userCartList){
                    //判断两个购物车是否有相同的商品
                    if(userce.getId().equals(tempce.getId())){
                        userce.setNum(userce.getNum() + tempce.getNum());
                        isExistElement = true;
                        break;
                    }
                }
                if(!isExistElement){ //不存在相同的商品
                    userCartList.add(tempce);
                }
            }
        return userCartList;
    }



}
