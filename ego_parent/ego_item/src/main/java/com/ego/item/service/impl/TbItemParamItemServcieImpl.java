package com.ego.item.service.impl;

import com.ego.commoms.utils.JsonUtils;
import com.ego.dubbo.service.TbItemParamItemDubboServcie;
import com.ego.item.pojo.ItemParamPojo;
import com.ego.item.service.TbItemParamItemServcie;
import com.ego.pojo.TbItemParamItem;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/15
 * @Description: com.ego.item.service.impl
 * @version: 1.0
 */
@Service
public class TbItemParamItemServcieImpl implements TbItemParamItemServcie {

    @Reference
    private TbItemParamItemDubboServcie tbItemParamItemDubboServcie;

    @Override
    public String showParam(Long itemId) {
        TbItemParamItem paramItem = tbItemParamItemDubboServcie.selectByItemId(itemId);
        if( paramItem != null ){
            String paramData = paramItem.getParamData();
            List<ItemParamPojo> list = JsonUtils.jsonToList(paramData, ItemParamPojo.class);
            StringBuffer sf = new StringBuffer();
            for(ItemParamPojo ipp : list){
                sf.append("<table border='0px' style='color: grey' width='100%'>");
                    for(int i = 0 ; i < ipp.getParams().size() ; i++){
                        sf.append("<tr>");
                            if(i == 0){     //第一列
                                sf.append("<td>"+ipp.getGroup()+"</td>");
                            }else{
                                sf.append("<td></td>");
                            }
                            //第二列
                            sf.append("<td>"+ipp.getParams().get(i).getK()+"</td>");
                            //第三列
                            sf.append("<td>"+ipp.getParams().get(i).getV()+"</td>");
                        sf.append("</tr>");
                    }
                sf.append("</table>");
                    //分割线
                    sf.append("<hr style='color:grey;'/>");
            }
            return sf.toString();
        }
        return "此商品没有规格参数！！！";
    }
}
