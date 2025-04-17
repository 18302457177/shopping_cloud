package com.cart.mapper;

import com.cart.domain.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86183
* @description 针对表【cart】的数据库操作Mapper
* @createDate 2025-03-29 19:19:20
* @Entity com.cart.domain.Cart
*/
@Mapper
public interface CartMapper extends BaseMapper<Cart> {

}




