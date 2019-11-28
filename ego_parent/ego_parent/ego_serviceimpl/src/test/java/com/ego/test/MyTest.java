package com.ego.test;

import com.ego.DubboApplication;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DubboApplication.class)
public class MyTest {
    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private TbItemDubboService tbItemDubboService;
    @Test
    public void test(){
        List<TbItem> list = tbItemDubboService.selectByPage(1, 2);
        System.out.println(list);
    }
}
