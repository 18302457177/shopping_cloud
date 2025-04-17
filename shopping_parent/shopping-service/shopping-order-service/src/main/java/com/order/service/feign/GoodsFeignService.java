package com.order.service.feign;

import api.common.Result;
import com.order.feignDomain.Spu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "goods-service",path = "/goods")
public interface GoodsFeignService {

    @GetMapping("/queryGoodsDetail/{goodsId}")
    public Result queryGoodsDetail(@PathVariable("goodsId")Integer goodsId);

    @GetMapping("/querySpu/{spuId}")
    public Result<Spu> querySpu(@PathVariable("spuId")Integer spuId);

}
