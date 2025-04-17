package com.cart.service.impl;

import api.common.PageResult;
import api.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cart.domain.Cart;
import com.cart.dto.CartDto;
import com.cart.service.CartService;
import com.cart.mapper.CartMapper;
import com.cart.util.JSONarrayUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @author 86183
* @description 针对表【cart】的数据库操作Service实现
* @createDate 2025-03-29 19:19:20
*/
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart>
    implements CartService{

    @Autowired
    CartMapper cartMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 添加购物测
     *
     * @param
     * @return
     */
    @Override
    public Result addCart(CartDto cartDto) {

        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDto,cart);
        String s = JSONarrayUtil.ToString(cartDto.getSkuparamId());
        cart.setSkuparamId(s);
        int insert = cartMapper.insert(cart);
        if (insert<=0){
            return Result.fail("插入失败");
        }
        return Result.success();
    }

    /**
     * 查询购物车
     *
     * @param
     * @return
     */
    @Override
    public Result getCart(Integer userId) {

        List<Cart> carts = cartMapper.selectList(new LambdaQueryWrapper<Cart>()
                .eq(Cart::getUserId, userId));
        ArrayList<CartDto> cartDtos = new ArrayList<>();
        for (int i = 0; i < carts.size(); i++) {
            List<Integer> integers = JSONarrayUtil.ToList(carts.get(i).getSkuparamId());
            CartDto cartDto = new CartDto();
            BeanUtils.copyProperties(carts.get(i),cartDto);
            cartDto.setSkuparamId(integers);
            cartDtos.add(cartDto);
        }


        return Result.success(cartDtos);
    }

    @Override
    public void submitCart(ArrayList<Integer> cartList)throws Exception {

        List<Cart> carts = cartMapper.selectBatchIds(cartList);
        ArrayList<CartDto> cartDtos = new ArrayList<>();
        for (Cart cart : carts){
            List<Integer> integers = JSONarrayUtil.ToList(cart.getSkuparamId());
            CartDto cartDto = new CartDto();
            BeanUtils.copyProperties(cart,cartDto);
            cartDto.setSkuparamId(integers);
            cartDtos.add(cartDto);
        }
        if (!carts.isEmpty()){
            rabbitTemplate.convertAndSend("","submitCart",cartDtos);
        }
    }

    /**
     * 删除购物车
     *
     * @param userId
     * @param cartId
     * @return
     */
    @Transactional
    @Override
    public Result deleteCart(Integer userId, Integer cartId) {

        int delete = cartMapper.delete(new LambdaQueryWrapper<Cart>().eq(Cart::getUserId, userId).eq(Cart::getId, cartId));
        if (delete<=0){
            return Result.fail("删除购物车失败");
        }

        return Result.success();
    }

    /**
     * 批量删除购物车
     *
     * @param cartIds
     * @return
     */
    @Override
    public Result deleteCartList(ArrayList<Integer> cartIds) {

        int i = cartMapper.deleteBatchIds(cartIds);
        if(i<=0){
            return Result.fail("批量删除失败");
        }

        return Result.success();
    }

    /**
     * 用户查询自己的购物车
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Result getUserOwnCart(Integer userId, Integer pageNo, Integer pageSize) {
        Integer count = cartMapper.selectCount(new LambdaQueryWrapper<Cart>().eq(Cart::getUserId, userId));
        PageHelper.startPage(pageNo,pageSize);
        List<Cart> carts = cartMapper.selectList(new LambdaQueryWrapper<Cart>().eq(Cart::getUserId, userId));
        PageResult<Cart> result = new PageResult<>();
        result.setData(carts);
        result.setTotal(count);
        return Result.success(result);
    }

    /**
     * 修改购物车数量
     * @return
     */
    @Transactional
    @Override
    public Result updateCartCount(Integer cartId, Integer count) {

        Cart cart = cartMapper.selectById(cartId);
        cart.setCount(count);
        cartMapper.updateById(cart);

        return Result.success();
    }
}




