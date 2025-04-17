package com.user.service;

import api.common.Result;
import com.user.domain.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86183
* @description 针对表【user_address】的数据库操作Service
* @createDate 2025-04-11 19:11:01
*/
public interface UserAddressService extends IService<UserAddress> {

    /**
     * 查询默认收货地址
     * @param userId
     * @return
     */
    Result<UserAddress> getDefaultAddress(Integer userId);
}
