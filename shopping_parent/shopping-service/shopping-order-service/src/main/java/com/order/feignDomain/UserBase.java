package com.order.feignDomain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
@Data
public class UserBase {
    /**
     *
     */
    @TableId
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 出生日期
     */
    private String birthday;

    private String phone;

    private Integer status;

    private Integer storeId;

    private Integer auth;

    private String email;

    private String name;
}
