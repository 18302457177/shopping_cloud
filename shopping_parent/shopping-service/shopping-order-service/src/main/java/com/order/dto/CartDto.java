package com.order.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 提交购物车
 *
 */
@Data
public class CartDto implements Serializable {
    /**
     * 
     */
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
    private List<Integer> skuparamId;

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