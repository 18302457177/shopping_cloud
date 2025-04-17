package com.goods.service;

import api.common.Result;
import com.goods.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.goods.dto.AddCategoryDto;
import com.goods.dto.CategoryDto;

import java.util.List;

/**
* @author 86183
* @description 针对表【category】的数据库操作Service
* @createDate 2025-02-26 14:39:55
*/
public interface CategoryService extends IService<Category> {

    /**
     * 查询一级分类
     * @param storeId
     * @return
     */
    Result<Category> getFirstCategory(Integer storeId);

    /**
     * 查询二级分类
     * @param storeId
     * @param parentId
     * @return
     */
    Result<Category> getSecondCategory(Integer storeId, Integer parentId);

    /**
     * 查询商品分类(树形结构)
     * @param storeId
     * @return
     */
    Result<List<CategoryDto>> getCategories(Integer storeId);

    /**
     * 添加分类
     * @param addGoodsDto
     * @return
     */
    Result addCategory(AddCategoryDto addCategoryDto);
}
