package com.ego.cart.service;

import com.ego.commoms.pojo.CartEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/19
 * @Description: com.ego.cart.service
 * @version: 1.0
 */
public interface CartService {

    boolean addCart(Long id, int num, HttpServletRequest req, HttpServletResponse resp);

    List<CartEntity> getCartProduction(HttpServletRequest req, HttpServletResponse resp);
}
