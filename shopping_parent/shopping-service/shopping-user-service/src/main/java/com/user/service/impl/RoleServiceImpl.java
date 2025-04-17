package com.user.service.impl;

import api.common.PageResult;
import api.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.user.domain.Role;
import com.user.service.RoleService;
import com.user.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86183
* @description 针对表【role】的数据库操作Service实现
* @createDate 2025-02-25 20:04:30
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    RoleMapper roleMapper;

    /**
     * 查询店铺角色列表
     *
     * @param storeId
     * @return
     */
    @Override
    public Result<List<Role>> getRoleList(Integer storeId) {

        List<Role> roles = roleMapper.selectList(new LambdaQueryWrapper<Role>().eq(Role::getStoreId, storeId));

        return Result.success(roles);
    }

    /**
     * 角色分页
     *
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Result<PageResult<Role>> rolePage(Integer storeId, Integer pageNo, Integer pageSize) {

        Integer total = roleMapper.selectCount(new LambdaQueryWrapper<Role>().eq(Role::getStoreId,storeId));
        PageHelper.startPage(pageNo,pageSize);
        Result<List<Role>> roleList = getRoleList(storeId);
        PageResult<Role> result = new PageResult<>();
        result.setTotal(total);
        result.setData(roleList.getData());

        return Result.success(result);
    }
}




