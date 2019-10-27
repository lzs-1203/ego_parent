package com.ego.item.pojo;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/15
 * @Description: com.ego.item.pojo
 * @version: 1.0
 */
public class SmallParam {

    private String k;
    private String v;

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public SmallParam(){

    }

    public SmallParam(String k, String v) {
        this.k = k;
        this.v = v;
    }
}
