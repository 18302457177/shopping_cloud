package com.user.controller;

import api.common.PageResult;
import api.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.user.domain.Role;
import com.user.domain.UserAddress;
import com.user.domain.UserBase;
import com.user.domain.UserRole;
import com.user.dto.PhoneLoginDto;
import com.user.dto.UserListDto;
import com.user.service.RoleService;
import com.user.service.UserAddressService;
import com.user.service.UserBaseService;
import com.user.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserController {

    @Autowired
    UserBaseService userBaseService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserAddressService userAddressService;


    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody UserBase userBase){
        return userBaseService.login(userBase);
    }

    @ApiOperation("手机号登录")
    @PostMapping("/phoneLogin")
    public Result phoneLogin(@RequestBody PhoneLoginDto phoneLoginDto){
        return userBaseService.phoneLogin(phoneLoginDto.getPhone(),phoneLoginDto.getCode());
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout/{userId}")
    public Result logout(@PathVariable("userId")Integer userId){
        return userBaseService.logout(userId);
    }

    @ApiOperation("查询用户列表")
    @GetMapping("/getUserList/{storeId}")
    public Result<List<UserListDto>> getUserList(@PathVariable("storeId") Integer storeId){
        return userBaseService.getUserList(storeId);
    }

    @ApiOperation("用户分页")
    @GetMapping("/pageUser/{storeId}/{pageNo}/{pageSize}")
    public Result<PageResult<UserListDto>> userPage(@PathVariable("storeId") Integer storeId,
                                                    @PathVariable("pageNo") Integer pageNo,
                                                    @PathVariable("pageSize") Integer pageSize){
        return userBaseService.userPage(storeId,pageNo,pageSize);
    }

    @ApiOperation("删除店铺用户")
    @DeleteMapping("/delete/{storeId}/{userId}")
    public Result deleteUser(@PathVariable("storeId")Integer storeId,@PathVariable("userId")Integer userId){

        userBaseService.update(new LambdaUpdateWrapper<UserBase>().eq(UserBase::getId,userId)
                .eq(UserBase::getStoreId,storeId)
                .set(UserBase::getStoreId,null));
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,userId));

        return Result.success();
    }

    @ApiOperation("启用或禁用店铺用户")
    @PostMapping("/changeUserStatus")
    public void changeUserStatus(@RequestBody UserBase userBase){
        userBaseService.update(new LambdaUpdateWrapper<UserBase>()
                .eq(UserBase::getId,userBase.getId())
                .eq(UserBase::getStoreId,userBase.getStoreId())
                .set(UserBase::getStatus,userBase.getStatus()==1?1:0));
    }


    @ApiOperation("查询店铺角色列表")
    @GetMapping("/getRoleList/{storeId}")
    public Result<List<Role>> getRoleList(@PathVariable("storeId")Integer storeId){
        return roleService.getRoleList(storeId);
    }

    @ApiOperation("添加角色")
    @PostMapping("/addRole")
    public Result addRole(@RequestBody Role role){
        boolean save = roleService.save(role);
        return save?Result.success():Result.fail("添加失败");
    }

    @ApiOperation("搜索用户列表用户")
    @PostMapping("/searchUser")
    public Result searchUser(@RequestBody UserBase userBase){
        return userBaseService.searchUser(userBase.getStoreId(),userBase.getName());
    }

    @ApiOperation("添加用户")
    @PostMapping("/addStoreUser")
    public Result addStoreUser(@RequestBody UserBase userBase){
        return userBaseService.addStoreUser(userBase);
    }

    @ApiOperation("分页查询店铺角色列表")
    @GetMapping("/pageRole/{storeId}/{pageNo}/{pageSize}")
    public Result<PageResult<Role>> rolePage(@PathVariable("storeId") Integer storeId,
                                             @PathVariable("pageNo")Integer pageNo,
                                             @PathVariable("pageSize")Integer pageSize){
        return roleService.rolePage(storeId,pageNo,pageSize);
    }

    @ApiOperation("查询用户信息")
    @GetMapping("/queryUser/{userId}")
    public Result<UserBase> queryUser(@PathVariable("userId")Integer userId){
        return userBaseService.queryUser(userId);
    }

    @ApiOperation("查询默认收货地址")
    @GetMapping("/getDefaultAddress/{userId}")
    public Result<UserAddress> getDefaultAddress(@PathVariable("userId")Integer userId){
        return userAddressService.getDefaultAddress(userId);
    }


}
