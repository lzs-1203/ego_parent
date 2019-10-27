package com.ego.order.pojo;

import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/20
 * @Description: com.ego.order.pojo
 * @version: 1.0
 */
public class OrderParam {

    private String payment;
    private Integer paymentType;
    private TbOrderShipping orderShipping;
    private List<TbOrderItem> orderItems = new ArrayList<>();//首先创建一个空的集合，防止空指针异常

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderParam() {
    }

    public OrderParam(String payment, Integer paymentType, TbOrderShipping orderShipping, List<TbOrderItem> orderItems) {
        this.payment = payment;
        this.paymentType = paymentType;
        this.orderShipping = orderShipping;
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "OrderParam{" +
                "payment='" + payment + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", orderShipping=" + orderShipping +
                ", orderItems=" + orderItems +
                '}';
    }
}
