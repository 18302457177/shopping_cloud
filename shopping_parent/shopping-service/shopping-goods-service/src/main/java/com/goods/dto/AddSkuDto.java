package com.goods.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddSkuDto {
    private String skuName;
    private List<String> param;
}
