package com.ego.commoms.pojo;

public class EgoResult {

    private Integer status;
    private Object data;
    private String msg;

    //为线程安全，在此只是声明，在此实体类中每一个方法中需要创建一个EgoResult对象
    private static EgoResult er = new EgoResult();

    public static EgoResult ok(){
        er.setStatus(200);
        er.setData(null);
        er.setMsg("");
        return er;
    }

    /*使用方法的重载，实现使用构造方法直接对实体类的属性赋值*/
    public static EgoResult ok(Object data){
        er.setStatus(200);
        er.setData(data);
        er.setMsg("");
        return er;
    }

    public static EgoResult error(String msg){
        er.setMsg(msg);
        er.setStatus(500);
        er.setData(null);
        return  er;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
