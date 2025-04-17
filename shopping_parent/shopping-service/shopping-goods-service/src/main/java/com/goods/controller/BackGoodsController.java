package com.goods.controller;

import api.common.Result;
import com.goods.domain.Category;
import com.goods.domain.Spu;
import com.goods.dto.AddCategoryDto;
import com.goods.dto.AddGoodsDto;
import com.goods.dto.CategoryDto;
import com.goods.dto.SkuDto;
import com.goods.service.CategoryService;
import com.goods.service.SkuService;
import com.goods.service.SpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
@Api(tags = "店铺后台商品接口")
public class BackGoodsController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    SpuService spuService;

    @Autowired
    SkuService skuService;

    @ApiOperation("查询商品一级分类")
    @GetMapping("/getFirstCategory/{storeId}")
    public Result<Category> getFirstCategory(@PathVariable("storeId") Integer storeId){
        return categoryService.getFirstCategory(storeId);
    }

    @ApiOperation("查询商品二或三级级分类")
    @GetMapping("/getSecondCategory/{storeId}/{parentId}")
    public Result<Category> getFirstCategory(@PathVariable("storeId") Integer storeId,
                                             @PathVariable("parentId")Integer parentId){
        return categoryService.getSecondCategory(storeId,parentId);
    }

    @ApiOperation("查询商品分类(树形结构)")
    @GetMapping("/getCategories/{storeId}")
    public Result<List<CategoryDto>> getCategories(@PathVariable("storeId") Integer storeId){
        return categoryService.getCategories(storeId);
    }

    @ApiOperation("查询商品静态分类")
    @GetMapping("/getStaticCategory/{storeId}/{category3Id}")
    public Result<List<Spu>> getStaticCategory(@PathVariable("storeId")Integer storeId,@PathVariable("category3Id")Integer categoryId){
        return spuService.getStaticCategory(storeId,categoryId);
    }

    @ApiOperation("查询商品动态参数")
    @GetMapping("/getDynamic/{storeId}/{category3Id}")
    public Result<List<SkuDto>> getDynamic(@PathVariable("storeId")Integer storeId,@PathVariable("category3Id")Integer categoryId){
        return skuService.getDynamic(storeId,categoryId);
    }

    @ApiOperation("查询本店铺全部商品")
    @GetMapping("/getAllGoods/{storeId}")
    public Result<List<Spu>> getAllGoodsByStoreId(@PathVariable("storeId") Integer storeId){
        return spuService.getAllGoodsByStoreId(storeId);
    }

    @ApiOperation("分页查询本店商品")
    @GetMapping("/getGoodsPage/{storeId}/{pageNo}/{pageSize}")
    public Result getGoodsPage(@PathVariable("storeId")Integer storeId,
                                          @PathVariable("pageNo")Integer pageNo,
                                          @PathVariable("pageSize")Integer pageSize){
        return spuService.getGoodsPage(storeId,pageNo,pageSize);
    }

    @ApiOperation("添加商品")
    @PostMapping("/addGoods")
    public Result addGoods(@RequestBody AddGoodsDto addGoodsDto){
        return spuService.addGoods(addGoodsDto);

    }

    @ApiOperation("添加分类")
    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody AddCategoryDto addCategoryDto){
        return categoryService.addCategory(addCategoryDto);
    }

    @ApiOperation("查询商品详细信息")
    @GetMapping("/queryGoodsDetail/{goodsId}")
    public Result queryGoodsDetail(@PathVariable("goodsId")Integer goodsId){
        return spuService.getGoodsDetail(goodsId);
    }

    @ApiOperation("查询spu")
    @GetMapping("/querySpu/{spuId}")
    public Result<Spu> querySpu(@PathVariable("spuId")Integer spuId){
        return spuService.querySpu(spuId);
    }

    @ApiOperation("删除商品(spu)")
    @DeleteMapping("/deleteSpu/{spuId}")
    public Result deleteSpu(@PathVariable("spuId")Integer spuId){
        return spuService.deleteSpu(spuId);
    }

}
