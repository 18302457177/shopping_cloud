package com.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName role
 */
@TableName(value ="role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 
     */
    private String role;

    /**
     * 
     */
    private Integer storeId;

    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}