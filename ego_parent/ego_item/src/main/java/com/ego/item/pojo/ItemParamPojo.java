package com.ego.item.pojo;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/15
 * @Description: com.ego.item.pojo
 * @version: 1.0
 */
public class ItemParamPojo {


    private String group;
    private List<SmallParam> params;


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<SmallParam> getParams() {
        return params;
    }

    public void setParams(List<SmallParam> params) {
        this.params = params;
    }
}
