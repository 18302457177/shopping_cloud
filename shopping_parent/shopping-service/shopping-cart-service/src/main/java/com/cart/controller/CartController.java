package com.cart.controller;

import api.common.Result;
import com.cart.domain.Cart;
import com.cart.dto.CartDto;
import com.cart.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
@Api(tags = "购物车接口")
public class CartController {

    @Autowired
    CartService cartService;

    @ApiOperation("添加购物车")
    @PostMapping("/addCart")
    public Result addCart(@RequestBody CartDto cartDto){
        return cartService.addCart(cartDto);
    }

    @ApiOperation("提交购物车")
    @PostMapping("/submitCart")
    public void submitCart(@RequestParam("cartList")ArrayList<Integer> cartList) throws Exception {
        cartService.submitCart(cartList);
    }


    @ApiOperation("查询购物车")
    @GetMapping("/getCart/{userId}")
    public Result getCart(@PathVariable("userId")Integer userId){
        return cartService.getCart(userId);
    }

    @ApiOperation("删除购物车")
    @DeleteMapping("/deleteCart/{userId}/{cartId}")
    public Result deleteCart(@PathVariable("userId")Integer userId,@PathVariable("cartId")Integer cartId){
        return cartService.deleteCart(userId,cartId);
    }

    @ApiOperation("批量删除购物车")
    @PostMapping("/deleteCartList")
    public Result deleteCartList(@RequestBody ArrayList<Integer> cartIds){
        return cartService.deleteCartList(cartIds);
    }

    @ApiOperation("用户查询自己的购物车")
    @GetMapping("/getUserOwnCart/{userId}/{pageNo}/{pageSize}")
    public Result getUserOwnCart(@PathVariable("userId")Integer userId,
                                 @PathVariable("pageNo")Integer pageNo,
                                 @PathVariable("pageSize")Integer pageSize){
        return cartService.getUserOwnCart(userId,pageNo,pageSize);
    }

    @ApiOperation("修改购物车商品数量")
    @PostMapping("/updateCartCount/{cartId}/{count}")
    public Result updateCartCount(@PathVariable("cartId")Integer cartId,
                                  @PathVariable("count")Integer count){
        return cartService.updateCartCount(cartId,count);
    }

}