package com.order.service.impl;

import api.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.order.config.SnowFlakeCompone;
import com.order.domain.OrderDetail;
import com.order.domain.OrderMaster;
import com.order.dto.CartDto;
import api.common.PageResult;
import com.order.feignDomain.Spu;
import com.order.feignDomain.UserAddress;
import com.order.feignDomain.UserBase;
import com.order.mapper.OrderDetailMapper;
import com.order.service.OrderMasterService;
import com.order.mapper.OrderMasterMapper;
import com.order.service.feign.CartFeignService;
import com.order.service.feign.GoodsFeignService;
import com.order.service.feign.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
* @author 86183
* @description 针对表【order_master】的数据库操作Service实现
* @createDate 2025-04-10 20:30:34
*/
@Service
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMaster>
    implements OrderMasterService{

    @Autowired
    OrderMasterMapper orderMasterMapper;

    @Autowired
    UserFeignService userFeignService;

    @Autowired
    SnowFlakeCompone snowFlakeCompone;

    @Autowired
    GoodsFeignService goodsFeignService;

    @Autowired
    CartFeignService cartFeignService;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    /**
     * 生成订单
     *
     * @param cartDtos
     */

    @Transactional
    @Override
    public void submitCart(ArrayList<CartDto> cartDtos) {
        Integer userId = cartDtos.get(0).getUserId();
        UserBase userBase = userFeignService.queryUser(1).getData();
        UserAddress userAddress = userFeignService.getDefaultAddress(userId).getData();
        ArrayList<Integer> cartIds = new ArrayList<>();
        for (CartDto cartDto:cartDtos){
            cartIds.add(cartDto.getId());
            OrderMaster orderMaster = new OrderMaster();
            long sn = snowFlakeCompone.getInstance().nextId();
            orderMaster.setOrderSn(String.valueOf(sn));
            orderMaster.setCustomerId(userId);
            orderMaster.setShippingUser(userBase.getName());
            orderMaster.setProvince(userAddress.getProvinceName());
            orderMaster.setCity(userAddress.getCityName());
            orderMaster.setDistrict(userAddress.getAreaName());
            orderMaster.setAddress(userAddress.getAddress());
            orderMaster.setOrderMoney(BigDecimal.valueOf(cartDto.getTotalMoney()));
            orderMasterMapper.insert(orderMaster);
            OrderDetail orderDetail = new OrderDetail();
            Spu spu = goodsFeignService.querySpu(cartDto.getSpuId()).getData();
            System.out.println("spu = " + spu);
            orderDetail.setOrderId(sn);
            orderDetail.setProductId(Long.valueOf(spu.getId()));
            orderDetail.setProductName(spu.getName());
            orderDetail.setProductPrice(BigDecimal.valueOf(spu.getPrice()));
            orderDetailMapper.insert(orderDetail);
        }
        cartFeignService.deleteCartList(cartIds);
        
    }

    /**
     * 商铺查询本店订单
     *
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Result<OrderMaster> getStoreOrder(Integer storeId, Integer pageNo, Integer pageSize) {
        Integer count = orderMasterMapper.selectCount(new LambdaQueryWrapper<OrderMaster>().eq(OrderMaster::getStoreId, storeId));
        PageHelper.startPage(pageNo,pageSize);
        List<OrderMaster> orderMasters = orderMasterMapper.selectList(new LambdaQueryWrapper<OrderMaster>().eq(OrderMaster::getStoreId, storeId));
        PageResult<OrderMaster> result = new PageResult<>();
        result.setData(orderMasters);
        result.setTotal(count);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        return Result.success(result);
    }

    /**
     * 根据订单编号查询订单
     *
     * @param orderSn
     * @return
     */
    @Override
    public Result<OrderMaster> queryOrder(Long orderSn) {

        OrderMaster orderMaster = orderMasterMapper.selectOne(new LambdaQueryWrapper<OrderMaster>().eq(OrderMaster::getOrderSn, orderSn));
        if(orderMaster==null){
            return Result.fail("没有该订单");
        }

        return Result.success(orderMaster);
    }
}




