package com.goods.mapper;

import com.goods.domain.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86183
* @description 针对表【category】的数据库操作Mapper
* @createDate 2025-02-26 14:39:55
* @Entity com.goods.domain.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




