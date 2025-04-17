package com.cart.service;

import api.common.Result;
import com.cart.domain.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cart.dto.CartDto;

import java.util.ArrayList;

/**
* @author 86183
* @description 针对表【cart】的数据库操作Service
* @createDate 2025-03-29 19:19:20
*/
public interface CartService extends IService<Cart> {

    /**
     * 添加购物测
     * @param cart
     * @return
     */
    Result addCart(CartDto cartDto);

    /**
     * 查询购物车
     * @param
     * @return
     */
    Result getCart(Integer userId);

    void submitCart(ArrayList<Integer> cartList) throws Exception;

    /**
     * 删除购物车
     * @param userId
     * @param cartId
     * @return
     */
    Result deleteCart(Integer userId, Integer cartId);

    /**
     * 批量删除购物车
     * @param
     * @param cartIds
     * @return
     */
    Result deleteCartList(ArrayList<Integer> cartIds);

    /**
     * 用户查询自己的购物车
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Result getUserOwnCart(Integer userId, Integer pageNo, Integer pageSize);

    /**
     * 修改购物车数量
     *
     * @param count
     * @param cartId
     * @return
     */
    Result updateCartCount(Integer cartId, Integer count);
}
