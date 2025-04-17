package com.goods.vo;

import com.goods.domain.Spu;
import com.goods.dto.SkuDto;
import lombok.Data;

import java.util.List;

@Data
public class GoodsDetailVO extends Spu {

    private List<SkuDto> skuDtoList;

}
