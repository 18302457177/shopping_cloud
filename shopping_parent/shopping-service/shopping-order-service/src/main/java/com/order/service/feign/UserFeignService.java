package com.order.service.feign;

import api.common.Result;
import com.order.feignDomain.UserAddress;
import com.order.feignDomain.UserBase;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "user-service",path = "/user")
public interface UserFeignService {

    @GetMapping("/queryUser/{userId}")
    public Result<UserBase> queryUser(@PathVariable("userId")Integer userId);

    @GetMapping("/getDefaultAddress/{userId}")
    public Result<UserAddress> getDefaultAddress(@PathVariable("userId")Integer userId);

}