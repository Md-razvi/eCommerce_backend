package com.e_commerce.project.service;
import com.e_commerce.project.model.Category;
import com.e_commerce.project.payload.CategoryDTO;
import com.e_commerce.project.payload.CategoryResponse;

import java.util.List;
public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDto);
    CategoryDTO deleteCategory(Long Id);
    CategoryDTO updateCategory(CategoryDTO categoryDto,Long categoryId);
}
