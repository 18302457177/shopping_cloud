package com.goods.dto;

import com.goods.domain.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto extends Category {
    private List<CategoryDto> children;
}
