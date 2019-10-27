package com.ego.portal.service.impl;

import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/13
 * @Description: com.ego.portal.service.impl
 * @version: 1.0
 */
@Service
public class TbContentServiceImpl implements TbContentService {


    @Autowired
    private RedisTemplate<String, Object> template;

    @Reference
    private TbContentDubboService tbContentDubboService;

    @Value("${bigad.redis.key}")
    private String redisKey;

    @Value("${bigad.mysql.id}")
    private Long mysqlId;

    @Override
    public String showBigImg() {
        //判断集群中是否有对应的数据
        //没有则从数据库中查询，有则直接取出
        //System.out.println(redisKey+"----"+mysqlId);   //bigad----89
        if( !template.hasKey(redisKey) ){
            List<TbContent> list = tbContentDubboService.selectByCategoryid2(mysqlId);
            System.out.println(list.toArray());
            List<Map<String,Object>> maplist = new ArrayList<>();
            for(TbContent tc : list){
                Map<String, Object> map = new HashMap<>();
                map.put("srcB",tc.getPic2());
                map.put("height",240);
                map.put("alt","图片加载失败");
                map.put("width",670);
                map.put("src",tc.getPic());
                map.put("widthB",670);
                map.put("href",tc.getUrl());
                map.put("heightB",240);
                maplist.add(map);
            }
            template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
            template.opsForValue().set(redisKey,maplist);
        }
        template.setValueSerializer(new StringRedisSerializer());
        String value = template.opsForValue().get(redisKey).toString();
        return value;
    }
}
