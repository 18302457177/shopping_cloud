package com.goods.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName sku
 */
@TableName(value ="sku")
@Data
public class Sku implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 产品id
     */
    private Integer spuId;

    /**
     * 商品标题
     */
    private String param;

    /**
     * 商品图片
     */
    private Object images;

    /**
     * 是否上架(0未上架,1上架)
     */
    private Integer saleable;

    /**
     * 是否有效(0有效，1无效)
     */
    private Integer valid;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date lastUpdateTime;

    /**
     * (0未删除，1已删除)
     */
    private Integer isDeleted;

    private Integer category3Id;

    private Integer storeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}