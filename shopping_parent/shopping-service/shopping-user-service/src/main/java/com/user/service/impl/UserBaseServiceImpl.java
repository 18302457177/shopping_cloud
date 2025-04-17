package com.user.service.impl;

import api.code.ApiCode;
import api.common.PageResult;
import api.common.Result;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.user.domain.Role;
import com.user.domain.UserRole;
import com.user.dto.UserListDto;
import com.user.mapper.RoleMapper;
import com.user.mapper.UserRoleMapper;
import com.user.service.UserBaseService;
import com.user.domain.UserBase;
import com.user.mapper.UserBaseMapper;
import com.utils.jwt.JwtUtil;
import com.utils.jwt.SaltMd5Util;
import com.user.config.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author 86183
* @description 针对表【user_base】的数据库操作Service实现
* @createDate 2025-02-22 19:21:51
*/
@Service
public class UserBaseServiceImpl extends ServiceImpl<UserBaseMapper, UserBase>
    implements UserBaseService {


    @Autowired
    UserBaseMapper userBaseMapper;

    @Autowired
    RedisCache redisCache;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Result login(UserBase userBase) {

        LambdaQueryWrapper<UserBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBase::getUsername,userBase.getUsername());
        UserBase userBase1 = userBaseMapper.selectOne(queryWrapper);
        if (userBase1 == null){
            return new Result<>(ApiCode.FAIL_CONTANT,null,"没有该用户");
        }
        String password = userBase.getPassword();
        if (SaltMd5Util.verifySaltPassword(password,userBase1.getPassword())){
            String jwt = JwtUtil.createJWT(userBase1.getId().toString());
            redisCache.setCacheObject("login:"+userBase1.getId(), JSON.toJSONString(userBase1),3, TimeUnit.HOURS);
            return new Result(ApiCode.SUCCESS_CONTANT,jwt);
        }

        return new Result(ApiCode.FAIL_CONTANT,null,"用户名或密码错误");
    }

    @Override
    public Result phoneLogin(String phone, String code) {
        UserBase userBase = userBaseMapper.selectOne(new LambdaQueryWrapper<UserBase>().eq(UserBase::getPhone, phone));
        if(userBase==null){
            return Result.fail("没有该用户");
        }
        String codeCache = redisCache.getCacheObject(phone);
        if (code.equals(codeCache)){
            String jwt = JwtUtil.createJWT(userBase.getId().toString());
            redisCache.setCacheObject("login:"+phone, JSON.toJSONString(userBase),3, TimeUnit.HOURS);
            return new Result(ApiCode.SUCCESS_CONTANT,jwt);
        }
        return Result.fail("验证码错误");
    }

    /**
     * 退出登录
     *
     * @param userId
     * @return
     */
    @Override
    public Result logout(Integer userId) {

        boolean b = redisCache.deleteObject("login:" + userId);
        if (b){
            return Result.success();
        }
        return Result.fail("退出失败");
    }

    /**
     * 分页查询用户
     *
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Result<PageResult<UserListDto>> userPage(Integer storeId, Integer pageNo, Integer pageSize) {

        Integer total = userBaseMapper.selectCount(new LambdaQueryWrapper<UserBase>().eq(UserBase::getStoreId, storeId));
        PageHelper.startPage(pageNo,pageSize);
        Result<List<UserListDto>> userList = getUserList(storeId);

        PageInfo<UserListDto> pageInfo = new PageInfo<>(userList.getData());
        PageResult<UserListDto> result = new PageResult<>();
        result.setTotal(total);
        result.setData(userList.getData());
        result.setPageNo(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());

        return Result.success(result);
    }

    @Override
    public Result<List<UserListDto>> getUserList(Integer storeId) {

        List<UserBase> users = userBaseMapper.selectList(new LambdaQueryWrapper<UserBase>().eq(UserBase::getStoreId, storeId));
        if (users.isEmpty()){
            return Result.fail("查询用户列表为空");
        }
        ArrayList<UserListDto> userListDtos = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            List<UserRole> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, users.get(i).getId()));
            UserListDto userListDto = new UserListDto();
            ArrayList<String> roles = new ArrayList<>();
            for (int j = 0; j < userRoles.size(); j++) {
                Role role = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getId, userRoles.get(j).getRoleId()));
                roles.add(role.getRole());
            }

            BeanUtils.copyProperties(users.get(i),userListDto);
            userListDto.setRole(roles);
            userListDtos.add(userListDto);
        }
        return Result.success(userListDtos);
    }

    /**
     * 搜索用户列表用户
     *
     * @param storeId
     * @param userName
     * @return
     */
    @Override
    public Result searchUser(Integer storeId, String userName) {

        UserBase userBase = userBaseMapper.selectOne(new LambdaQueryWrapper<UserBase>()
                .eq(UserBase::getStoreId, storeId)
                .eq(UserBase::getName, userName));
        if (userBase == null){
            return Result.fail("没有该用户");
        }
        userBase.setPassword(null);
        userBase.setPhone(null);
        return Result.success(userBase);

    }

    /**
     * 添加商铺用户
     *
     * @param userBase
     * @return
     */
    @Override
    public Result addStoreUser(UserBase userBase) {

        String password = userBase.getPassword();

        UserBase userBase1 = userBaseMapper.selectOne(new LambdaQueryWrapper<UserBase>()
                .eq(UserBase::getUsername, userBase.getUsername()));
        if (userBase1 == null){
            return Result.fail("没有该用户");
        }
        boolean b = SaltMd5Util.verifySaltPassword(password, userBase1.getPassword());
        if (!b){
            return Result.fail("密码错误");
        }
        userBase1.setStoreId(userBase.getStoreId());
        userBaseMapper.updateById(userBase1);
        return Result.success();


    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public Result<UserBase> queryUser(Integer userId) {
        UserBase userBase = userBaseMapper.selectById(userId);
        if(userBase==null){
            return Result.fail("没有该用户");
        }
        return Result.success(userBase);
    }
}




