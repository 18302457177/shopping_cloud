package com.order.service.feign;

import api.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
@Component
@FeignClient(name = "cart-service",path = "/cart")
public interface CartFeignService {

    @DeleteMapping("/deleteCartList")
    public Result deleteCartList(@RequestParam("cartIds")ArrayList<Integer> cartIds);

}
