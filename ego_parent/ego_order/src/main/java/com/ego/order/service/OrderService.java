package com.ego.order.service;

import com.ego.order.pojo.OrderEntity;
import com.ego.order.pojo.OrderParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/20
 * @Description: com.ego.order.service
 * @version: 1.0
 */
public interface OrderService {


    List<OrderEntity> selectList(List<Long> ids , HttpServletRequest req);

    Map<String , Object> create(OrderParam orderParam, HttpServletRequest request);

}
