package com.ego.order.controller;

import com.ego.order.pojo.OrderParam;
import com.ego.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/20
 * @Description: com.ego.order.controller
 * @version: 1.0
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;


    @RequestMapping("/order/order-cart.html")
    public String showOrder(@RequestParam("id") List<Long> ids, HttpServletRequest req , Model model){
        model.addAttribute("cartList", orderService.selectList(ids, req));
        return "order-cart";
    }

    @RequestMapping("/order/create.html")
    public String createOrder(OrderParam orderParam, HttpServletRequest request, Model model){
        Map<String, Object> map = orderService.create(orderParam, request);
        if(map != null){
            model.addAllAttributes(map);
            return "success";
        }
        model.addAttribute("message","订单创建失败");
        return "exception";
    }
}
