package com.order.feignDomain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class UserAddress {
    /**
     * 收货地址id
     */
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
}
