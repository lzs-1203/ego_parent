package com.ego.commons.pojo;

public class EgoResult {
    private int status;
    private String msg;
    private Object data;

    public static EgoResult ok(){
        EgoResult er = new EgoResult();
        er.setStatus(200);
        return er;
    }

    public static EgoResult ok(Object data){
        EgoResult er = new EgoResult();
        er.setStatus(200);
        er.setData(data);
        return er;
    }

    public static EgoResult error(String msg){
        EgoResult er = new EgoResult();
        er.setStatus(400);
        er.setMsg(msg);
        return er;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
