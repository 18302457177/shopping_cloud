package com.user.service;

import api.common.PageResult;
import api.common.Result;
import com.user.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86183
* @description 针对表【role】的数据库操作Service
* @createDate 2025-02-25 20:04:30
*/
public interface RoleService extends IService<Role> {

    /**
     * 查询店铺角色列表
     * @param storeId
     * @return
     */
    Result<List<Role>> getRoleList(Integer storeId);

    /**
     * 角色分页
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Result<PageResult<Role>> rolePage(Integer storeId, Integer pageNo, Integer pageSize);
}
