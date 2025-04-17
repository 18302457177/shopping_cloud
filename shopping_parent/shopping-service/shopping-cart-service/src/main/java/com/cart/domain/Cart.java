package com.cart.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 
 * @TableName cart
 */
@TableName(value ="cart",autoResultMap = true)
@Data
public class Cart implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer spuId;

    /**
     * 
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private String skuparamId;

    /**
     * 
     */
    private Integer count;

    /**
     * 
     */
    private Integer selected;

    /**
     * 
     */
    private Date timestamp;

    /**
     * 
     */
    private String img;

    private double totalMoney;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}