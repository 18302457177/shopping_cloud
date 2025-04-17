package com.user.mapper;

import com.user.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86183
* @description 针对表【role】的数据库操作Mapper
* @createDate 2025-02-25 20:04:30
* @Entity com.user.domain.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}




