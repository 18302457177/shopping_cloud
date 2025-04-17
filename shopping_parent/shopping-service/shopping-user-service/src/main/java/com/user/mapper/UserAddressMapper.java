package com.user.mapper;

import com.user.domain.UserAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86183
* @description 针对表【user_address】的数据库操作Mapper
* @createDate 2025-04-11 19:11:01
* @Entity com.user.domain.UserAddress
*/
@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddress> {

}




