package com.cart.dto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 *
 * @TableName cart
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


}
