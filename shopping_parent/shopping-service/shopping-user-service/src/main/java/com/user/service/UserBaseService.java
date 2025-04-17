package com.user.service;

import api.common.PageResult;
import api.common.Result;
import com.user.domain.UserBase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.user.dto.UserListDto;

import java.util.List;

/**
* @author 86183
* @description 针对表【user_base】的数据库操作Service
* @createDate 2025-02-22 19:21:51
*/
public interface UserBaseService extends IService<UserBase> {

    Result login(UserBase userBase);

    /**
     * 查询所有用户
     * @param storeId
     * @return
     */
    Result<List<UserListDto>> getUserList(Integer storeId);

    /**
     * 分页查询用户
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Result<PageResult<UserListDto>> userPage(Integer storeId, Integer pageNo, Integer pageSize);


    /**
     * 退出登录
     * @param userId
     * @return
     */
    Result logout(Integer userId);

    /**
     * 搜索用户列表用户
     * @param storeId
     * @param userName
     * @return
     */
    Result searchUser(Integer storeId, String userName);

    /**
     * 添加商铺用户
     * @param userBase
     * @return
     */
    Result addStoreUser(UserBase userBase);

    Result phoneLogin(String phone, String code);

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    Result<UserBase> queryUser(Integer userId);
}
