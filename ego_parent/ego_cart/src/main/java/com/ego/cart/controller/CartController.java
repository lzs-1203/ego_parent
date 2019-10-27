package com.ego.cart.controller;

import com.ego.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/19
 * @Description: com.ego.cart.controller
 * @version: 1.0
 */
@Controller
public class CartController {


    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/add/{id}.html")
    public String showCartSucess(@PathVariable Long id, int num, HttpServletRequest req, HttpServletResponse resp){
        cartService.addCart(id, num, req, resp);
        return "cartSuccess";
    }

    @GetMapping("/cart/cart.html")
    public String showCart(HttpServletRequest req,HttpServletResponse resp, Model model){
        model.addAttribute("cartList",cartService.getCartProduction(req,resp));
        return "cart";
    }

}
