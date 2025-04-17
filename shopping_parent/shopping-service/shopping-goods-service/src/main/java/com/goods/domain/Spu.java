package com.goods.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName spu
 */
@TableName(value ="spu")
@Data
public class Spu implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 店铺id
     */
    private Integer storeId;

    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 销售状态(0下架，1上架)
     */
    private Integer saleStatus;

    /**
     * 三级分类id
     */
    private Integer category3Id;

    /**
     * 图片路径
     */
    private String img;

    /**
     * 商品描述
     */
    private String description;

    private Double price;

    private Double sales;

    private Double saleQuantity;

    private String video;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}