package com.goods.service.impl;

import api.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goods.domain.Category;
import com.goods.dto.AddCategoryDto;
import com.goods.dto.CategoryDto;
import com.goods.service.CategoryService;
import com.goods.mapper.CategoryMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 86183
* @description 针对表【category】的数据库操作Service实现
* @createDate 2025-02-26 14:39:54
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 查询一级分类
     *
     * @param storeId
     * @return
     */
    @Override
    public Result<Category> getFirstCategory(Integer storeId) {

        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .eq(Category::getParentId, 0)
                .eq(Category::getStoreId, storeId));
        if (!categories.isEmpty()){
            return Result.success(categories);
        }

        return Result.fail("没有分类");
    }

    /**
     * 查询二级分类
     *
     * @param storeId
     * @param parentId
     * @return
     */
    @Override
    public Result<Category> getSecondCategory(Integer storeId, Integer parentId) {

        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<Category>().eq(Category::getStoreId, storeId)
                .eq(Category::getParentId, parentId));
        if (!categories.isEmpty()){
            return Result.success(categories);
        }

        return Result.fail("没有数据");
    }

    /**
     * 查询商品分类(树形结构)
     *
     * @param storeId
     * @return
     */
    @Override
    public Result<List<CategoryDto>> getCategories(Integer storeId) {

        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<Category>().eq(Category::getStoreId, storeId));
        List<CategoryDto> tree = tree(categories, 0);

        return Result.success(tree);
    }

    public List<CategoryDto> tree(List<Category> categories,Integer parentId){
        if (categories.isEmpty()){
            return null;
        }
        ArrayList<CategoryDto> categoryDtos = new ArrayList<>();
        categories.stream().filter(n->n.getParentId()==parentId).forEach(item->{
            CategoryDto categoryDto = new CategoryDto();
            BeanUtils.copyProperties(item,categoryDto);
            categoryDto.setChildren(tree(categories.stream().filter(m->m.getParentId()!=parentId).toList(), item.getId()));
            categoryDtos.add(categoryDto);
        });
        return categoryDtos;
    }

    /**
     * 添加分类
     *
     * @param addGoodsDto
     * @return
     */
    @Override
    public Result addCategory(AddCategoryDto addCategoryDto) {

        Category category1 = new Category();
        category1.setParentId(0);
        category1.setSort(1);
        category1.setName(addCategoryDto.getCategory1());
        category1.setStoreId(addCategoryDto.getStoreId());
        category1.setChild(1);
        int insert1 = categoryMapper.insert(category1);
        Category category2 = new Category();
        category2.setParentId(0);
        category2.setSort(1);
        category2.setName(addCategoryDto.getCategory2());
        category2.setStoreId(addCategoryDto.getStoreId());
        category2.setChild(1);
        int insert2 = categoryMapper.insert(category2);
        Category category3 = new Category();
        category3.setParentId(0);
        category3.setSort(1);
        category3.setName(addCategoryDto.getCategory3());
        category3.setStoreId(addCategoryDto.getStoreId());
        category3.setChild(0);
        int insert3 = categoryMapper.insert(category3);
        return Result.success();
    }
}




