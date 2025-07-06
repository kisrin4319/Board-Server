package org.example.boardserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.boardserver.dto.CategoryDTO;

@Mapper
public interface CategoryMapper {
    public int register(CategoryDTO categoryDTO);

    public void updateCategory(CategoryDTO categoryDTO);

    public void deleteCategory(int categoryId);
}
