package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbUserDubboService;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/16
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbUserDubboServiceImpl implements TbUserDubboService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser selectByUser(TbUser tbUser) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(tbUser.getUsername()).andPasswordEqualTo(tbUser.getPassword());
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if(list != null && list.size() > 0 ){
            return list.get(0);
        }
        return null;
    }

    @Override
    public TbUser selectByUsernamePhone(String username, String phone) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria c = example.createCriteria();
        if( username != null && !username.equals("")){
            c = c.andUsernameEqualTo(username);
        }
        if( phone != null && !phone.equals("")){
            c = c.andPhoneEqualTo(phone);
        }
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insert(TbUser tbUser) {
        Date date = new Date();
        tbUser.setCreated(date);
        tbUser.setUpdated(date);
        int i = tbUserMapper.insertSelective(tbUser);
        if(i == 1){
            return 1;
        }
        return 0;
    }
}
