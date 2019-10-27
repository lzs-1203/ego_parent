package com.ego.item.service.impl;

import com.ego.commoms.pojo.SearchSolrResult;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/15
 * @Description: com.ego.item.service.impl
 * @version: 1.0
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Reference
    private TbItemDubboService tbItemDubboService;

    @Autowired
    private RedisTemplate<String, Object> template;

    @Value("${myredis.itemkey}")
    private String key;

    @Override
    public SearchSolrResult showImage(Long id) {
        String itemrdiskey = key + ":" + id;
        if( !template.hasKey(itemrdiskey) ){
            TbItem tbItem = tbItemDubboService.selectById(id);
            SearchSolrResult ssr = new SearchSolrResult();
            ssr.setId(id + "");
            ssr.setTitle(tbItem.getTitle());
            ssr.setSellPoint(tbItem.getSellPoint());
            ssr.setPrice(tbItem.getPrice());
            ssr.setImages( tbItem.getImage()!=null && !tbItem.getImage().equals("") ? tbItem.getImage().split(",") : new String[0]);
            template.opsForValue().set(itemrdiskey, ssr);
        }
        template.setValueSerializer(new Jackson2JsonRedisSerializer<SearchSolrResult>(SearchSolrResult.class));
        SearchSolrResult redisSsr = (SearchSolrResult) template.opsForValue().get(itemrdiskey);
        return redisSsr;
    }
}
