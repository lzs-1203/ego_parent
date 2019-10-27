package com.ego.servcie.impl;

import com.ego.commoms.pojo.EgoResult;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.pojo.TbItemDesc;
import com.ego.servcie.TbItemDescService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/7
 * @Description: com.ego.servcie.impl
 * @version: 1.0
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {

    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Override
    public EgoResult findById(Long id) {
        TbItemDesc tbItemDesc = tbItemDescDubboService.selectById(id);
        EgoResult egoResult = new EgoResult();
        egoResult.setData(tbItemDesc);
        egoResult.setStatus(200);
        return egoResult;
    }
}
