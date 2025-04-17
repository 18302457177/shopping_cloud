package com.user.service.impl;

import api.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.user.domain.UserAddress;
import com.user.service.UserAddressService;
import com.user.mapper.UserAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 86183
* @description 针对表【user_address】的数据库操作Service实现
* @createDate 2025-04-11 19:11:01
*/
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress>
    implements UserAddressService{

    @Autowired
    UserAddressMapper userAddressMapper;

    /**
     * 查询默认收货地址
     *
     * @param userId
     * @return
     */
    @Override
    public Result<UserAddress> getDefaultAddress(Integer userId) {

        UserAddress userAddress = userAddressMapper.selectOne(new LambdaQueryWrapper<UserAddress>().eq(UserAddress::getUid, userId));
        if (userAddress==null){
            return Result.fail("该用户没有默认地址");
        }
        return Result.success(userAddress);
    }
}




