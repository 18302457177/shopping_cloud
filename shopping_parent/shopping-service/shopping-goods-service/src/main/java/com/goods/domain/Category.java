package com.goods.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName category
 */
@TableName(value ="category")
@Data
public class Category implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 分类id
     */
    private String name;

    /**
     * 上级分类id
     */
    private Integer parentId;

    /**
     * 是否有下级分类(0没有,1有)
     */
    private Integer child;

    /**
     * 排名指数
     */
    private Integer sort;

    /**
     * 逻辑删除(0未删除，1已删除)
     */
    private Integer isDeleted;

    private Integer storeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}