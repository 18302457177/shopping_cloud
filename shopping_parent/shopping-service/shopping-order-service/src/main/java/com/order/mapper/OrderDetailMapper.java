package com.order.mapper;

import com.order.domain.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86183
* @description 针对表【order_detail】的数据库操作Mapper
* @createDate 2025-04-10 20:30:06
* @Entity com.order.domain.OrderDetail
*/
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




