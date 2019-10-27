package com.ego.commoms.EgoException;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/20
 * @Description: com.ego.commoms.EgoException
 * @version: 1.0
 */
public class ItemUnderStockException extends RuntimeException {

    public ItemUnderStockException(String message) {
        super(message);
    }
}
