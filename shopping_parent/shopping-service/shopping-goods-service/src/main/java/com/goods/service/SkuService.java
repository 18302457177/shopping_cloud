package com.goods.service;

import api.common.Result;
import com.goods.domain.Sku;
import com.baomidou.mybatisplus.extension.service.IService;
import com.goods.dto.SkuDto;

import java.util.List;

/**
* @author 86183
* @description 针对表【sku】的数据库操作Service
* @createDate 2025-02-27 14:06:45
*/
public interface SkuService extends IService<Sku> {

    /**
     * 店铺动态属性
     * @param storeId
     * @param categoryId
     * @return
     */
    Result<List<SkuDto>> getDynamic(Integer storeId, Integer categoryId);
}
