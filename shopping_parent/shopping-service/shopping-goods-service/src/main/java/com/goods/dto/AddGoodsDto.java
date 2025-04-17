package com.goods.dto;

import com.goods.domain.Sku;
import com.goods.domain.SkuParam;
import com.goods.domain.Spu;
import lombok.Data;

import java.util.List;

@Data
public class AddGoodsDto {

    private Integer category3_id;

    private Spu goodsData;

    private List<AddSkuDto> skuList;

    private String imgMd5;

    private String imgSuffix;

    private String videoMd5;

    private String videoSuffix;

}
