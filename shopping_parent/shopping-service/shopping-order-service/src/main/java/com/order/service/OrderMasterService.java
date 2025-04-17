package com.order.service;

import api.common.Result;
import com.order.domain.OrderMaster;
import com.baomidou.mybatisplus.extension.service.IService;
import com.order.dto.CartDto;

import java.util.ArrayList;

/**
* @author 86183
* @description 针对表【order_master】的数据库操作Service
* @createDate 2025-04-10 20:30:34
*/
public interface OrderMasterService extends IService<OrderMaster> {

    /**
     * 生成订单
     * @param cartDtos
     */
    void submitCart(ArrayList<CartDto> cartDtos);

    /**
     * 商铺查询本店订单
     *
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Result<OrderMaster> getStoreOrder(Integer storeId, Integer pageNo, Integer pageSize);

    /**
     * 根据订单编号查询订单
     * @param orderSn
     * @return
     */
    Result<OrderMaster> queryOrder(Long orderSn);
}
