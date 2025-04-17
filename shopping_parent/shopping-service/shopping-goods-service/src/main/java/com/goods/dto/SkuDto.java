package com.goods.dto;

import com.goods.domain.Sku;
import com.goods.domain.SkuParam;
import lombok.Data;

import java.util.List;

@Data
public class SkuDto extends Sku {

    private List<SkuParam> children;

}
