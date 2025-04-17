package com.goods.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName sku_param
 */
@TableName(value ="sku_param")
@Data
public class SkuParam implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 
     */
    private Integer skuId;

    /**
     * 
     */
    private String param;

    /**
     * 
     */
    private BigDecimal price;

    private Integer storeId;

    private Integer choose;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}