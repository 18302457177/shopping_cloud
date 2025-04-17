package com.goods.service.impl;

import api.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goods.domain.Sku;
import com.goods.domain.SkuParam;
import com.goods.dto.SkuDto;
import com.goods.mapper.SkuParamMapper;
import com.goods.service.SkuService;
import com.goods.mapper.SkuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 86183
* @description 针对表【sku】的数据库操作Service实现
* @createDate 2025-02-27 14:06:45
*/
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku>
    implements SkuService{

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    SkuParamMapper skuParamMapper;

    /**
     * 店铺动态属性
     *
     * @param storeId
     * @param categoryId
     * @return
     */
    @Override
    public Result<List<SkuDto>> getDynamic(Integer storeId, Integer categoryId) {

        List<Sku> skus = skuMapper.selectList(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getCategory3Id, categoryId)
                .eq(Sku::getStoreId, storeId));
        List<SkuParam> skuParams = skuParamMapper.selectList(new LambdaQueryWrapper<SkuParam>()
                .eq(SkuParam::getStoreId, storeId));
        ArrayList<SkuDto> skuDtos = new ArrayList<>();
        skus.forEach(item->{
            SkuDto skuDto = new SkuDto();
            BeanUtils.copyProperties(item,skuDto);
            List<SkuParam> list = skuParams.stream().filter(n -> n.getSkuId() == item.getId()).toList();
            skuDto.setChildren(list);
            skuDtos.add(skuDto);
        });
        return Result.success(skuDtos);
    }
}




