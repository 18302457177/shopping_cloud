package com.order.feignDomain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Spu implements Serializable {
    /**
     *
     */
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
}
