package com.ego.commoms.pojo;

/**
 * 封装数据个前台页面
 * @Auther: Constant.Wang
 * @Date: 2019/8/14
 * @Description: com.ego.search.pojo
 * @version: 1.0
 */
public class SearchSolrResult {

    private String id;
    private String [] images;
    private String sellPoint;
    private String title;
    private long price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public SearchSolrResult() {
    }

}
