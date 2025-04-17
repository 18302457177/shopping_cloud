package com.order.controller;

import api.common.Result;
import com.order.domain.OrderMaster;
import com.order.dto.CartDto;
import com.order.service.OrderMasterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.ast.Or;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/order")
@Api(tags = "订单接口")
public class OrderController {

    @Autowired
    OrderMasterService orderMasterService;

    @ApiOperation("接收购物车信息并生成订单")
    @RabbitListener(queuesToDeclare = @Queue("submitCart"))
    public void submitCart(ArrayList<CartDto> cartDtos){
        System.out.println("cartDtos = " + cartDtos);
        orderMasterService.submitCart(cartDtos);
    }

    @ApiOperation("商铺查询订单")
    @GetMapping("/getStoreOrder/{storeId}/{pageNo}/{pageSize}")
    public Result<OrderMaster> getStoreOrder(@PathVariable("storeId")Integer storeId,
                                             @PathVariable("pageNo")Integer pageNo,
                                             @PathVariable("pageSize")Integer pageSize){
        return orderMasterService.getStoreOrder(storeId,pageNo,pageSize);
    }

    @ApiOperation("根据订单编号查询订单")
    @GetMapping("/queryOder/{orderSn}")
    public Result<OrderMaster> queryOder(@PathVariable("orderSn")Long orderSn){
        return orderMasterService.queryOrder(orderSn);
    }


}
