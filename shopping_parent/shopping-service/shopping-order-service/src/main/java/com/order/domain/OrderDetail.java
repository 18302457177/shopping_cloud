package com.order.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName order_detail
 */
@TableName(value ="order_detail")
@Data
public class OrderDetail implements Serializable {
    /**
     * 订单详情表ID
     */
    @TableId(type = IdType.AUTO)
    private Long orderDetailId;

    /**
     * 订单表ID
     */
    private Long orderId;

    /**
     * 订单商品ID
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 购买商品数量
     */
    private Integer productCnt;

    /**
     * 购买商品单价
     */
    private BigDecimal productPrice;

    /**
     * 优惠分摊金额
     */
    private BigDecimal feeMoney;

    private Integer storeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}