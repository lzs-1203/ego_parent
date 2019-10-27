package com.ego.servcie.impl;

import com.ego.commoms.pojo.EasyUiDataGrid;
import com.ego.commoms.pojo.EgoResult;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.servcie.TbContentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/9
 * @Description: com.ego.servcie.impl
 * @version: 1.0
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Reference
    private TbContentDubboService tbContentDubboService;

    @Autowired
    private RedisTemplate<String , Object> redisTemplate;

    @Value("${bigad.redis.key}")
    private String redisKey;

    @Override
    public EasyUiDataGrid findByCategoryid(Long categoryId, int page, int rows) {
        EasyUiDataGrid dataGrid = new EasyUiDataGrid();
        dataGrid.setRows(tbContentDubboService.selectByCategoryid(categoryId,page,rows));
        dataGrid.setTotal(tbContentDubboService.countContent(categoryId));
        return dataGrid;
    }

    @Override
    public EgoResult save(TbContent tc) {
        int insert = tbContentDubboService.insert(tc);
        if(insert == 1){
            //同步
            if(redisTemplate.hasKey(redisKey)){
                List<Map<String, Object>> list = (List<Map<String, Object>>) redisTemplate.opsForValue().get(redisKey);
                Map<String , Object> map = new HashMap<>();
                map.put("srcB",tc.getPic2());
                map.put("height",240);
                map.put("alt","图片加载失败");
                map.put("width",670);
                map.put("src",tc.getPic());
                map.put("widthB",670);
                map.put("href",tc.getUrl());
                map.put("heightB",240);
                list.add(map);
                redisTemplate.opsForValue().set(redisKey,list);
            }
            return EgoResult.ok();
        }
        return EgoResult.error("增加内容失败");
    }

    @Override
    public EgoResult change(TbContent tc) {
        int update = tbContentDubboService.update(tc);
        if (update ==1){
            if(redisTemplate.hasKey(redisKey)){
                List<Map<String, Object>> list = (List<Map<String, Object>>) redisTemplate.opsForValue().get(redisKey);
                Map<String , Object> map = new HashMap<>();
                map.put("srcB",tc.getPic2());
                map.put("height",240);
                map.put("alt","图片加载失败");
                map.put("width",670);
                map.put("src",tc.getPic());
                map.put("widthB",670);
                map.put("href",tc.getUrl());
                map.put("heightB",240);
                list.add(map);
                redisTemplate.opsForValue().set(redisKey,list);
            }
            return EgoResult.ok();
        }
        return EgoResult.error("修改失败");
    }

    @Override
    public EgoResult delete(Long[] ids) {
        int delete = tbContentDubboService.delete(ids);
        if(delete ==1){
            if(redisTemplate.hasKey(redisKey)){
                Boolean delete1 = redisTemplate.delete(redisKey);
            }
            return EgoResult.ok();
        }
        return EgoResult.error("删除失败");
    }
}
