package com.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.order.domain.OrderDetail;
import com.order.service.OrderDetailService;
import com.order.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author 86183
* @description 针对表【order_detail】的数据库操作Service实现
* @createDate 2025-04-10 20:30:06
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




