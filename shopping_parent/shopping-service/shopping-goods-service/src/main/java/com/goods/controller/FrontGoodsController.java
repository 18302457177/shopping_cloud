package com.goods.controller;

import api.common.Result;
import com.goods.domain.Spu;
import com.goods.service.SpuService;
import com.goods.vo.GoodsDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods/frontGoods")
@Api(tags = "店铺前台商品接口")
public class FrontGoodsController {

    @Autowired
    SpuService spuService;

    @ApiOperation("查询商品")
    @GetMapping("/getGoods")
    public Result<Spu> getGoods(){
        return spuService.getAllGoods();
    }

    @ApiOperation("查询某个商品详细信息")
    @GetMapping("/getGoodsDetail/{spuId}")
    public Result<GoodsDetailVO> getGoodsDetail(@PathVariable("spuId") Integer id){
        return spuService.getGoodsDetail(id);
    }



}
