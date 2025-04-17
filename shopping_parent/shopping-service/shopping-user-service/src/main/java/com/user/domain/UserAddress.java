package com.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user_address
 */
@TableName(value ="user_address")
@Data
public class UserAddress implements Serializable {
    /**
     * 收货地址id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 收货人姓名
     */
    private String name;

    /**
     * 省代号
     */
    private String provinceCode;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市代号
     */
    private String cityCode;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区代号
     */
    private String areaCode;

    /**
     * 区名称
     */
    private String areaName;

    /**
     * 邮编
     */
    private String zip;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 手机
     */
    private String phone;

    /**
     * 固话
     */
    private String tel;

    /**
     * 地址类型
     */
    private String tag;

    /**
     * 是否默认，0-非默认，1-默认
     */
    private Integer isDefault;

    /**
     * 创建人
     */
    private String createdUser;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 最后修改人
     */
    private String modifiedUser;

    /**
     * 最后修改时间
     */
    private Date modifiedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}