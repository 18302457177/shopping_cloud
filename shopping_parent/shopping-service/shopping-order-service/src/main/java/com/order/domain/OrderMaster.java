package com.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName order_master
 */
@TableName(value ="order_master")
@Data
public class OrderMaster implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 下单人id
     */
    private Integer customerId;

    /**
     * 收货人姓名
     */
    private String shippingUser;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 地址
     */
    private String address;

    /**
     * 支付方式：1现金，2余额，3网银，4支付宝，5微信
     */
    private Integer paymentMethod;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 优惠金额
     */
    private BigDecimal districtMoney;

    /**
     * 运费金额
     */
    private BigDecimal shippingMoney;

    /**
     * 支付金额
     */
    private BigDecimal paymentMoney;

    /**
     * 快递公司名称
     */
    private String shippingCompName;

    /**
     * 快递单号
     */
    private String shippingSn;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 发货时间
     */
    private Date shippingTime;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 收货时间
     */
    private Date receiveTime;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 订单积分
     */
    private Integer orderPoint;

    private Integer storeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}