package com.e_commerce.project.service;

import com.e_commerce.project.config.MapperMethods;
import com.e_commerce.project.exception.ApiException;
import com.e_commerce.project.exception.ResourceNotFound;
import com.e_commerce.project.model.Category;
import com.e_commerce.project.payload.CategoryDTO;
import com.e_commerce.project.payload.CategoryResponse;
import com.e_commerce.project.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private MapperMethods mapperMethods;
      private Long nextId=1L;
    @Override
    public CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageDetail= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Category> categoryPage=categoriesRepository.findAll(pageDetail);
        List<Category> savedList= categoryPage.getContent();
        if(savedList.isEmpty()){
            throw new ApiException("No content is provided here");
        }
        List<CategoryDTO> categoryDTOS=savedList.stream()
                .map(e->mapperMethods.toDto(e))
                .collect(Collectors.toList());
        CategoryResponse userList=new CategoryResponse();
        userList.setContent(categoryDTOS);
        userList.setPageNumber(categoryPage.getNumber());
        userList.setPageSize(categoryPage.getSize());
        userList.setTotalElements(categoryPage.getTotalElements());
        userList.setTotalPages(categoryPage.getTotalPages());
        userList.setLastPage(categoryPage.isLast());
    return userList;

    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDto) {
        Category category=mapperMethods.toEntity(categoryDto);
        Category savedCategory=categoriesRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory!=null){
            throw new ApiException("Category with name "+category.getCategoryName()+" already exists");
        }
        Category category1=categoriesRepository.save(category);
        return mapperMethods.toDto(category1);
    }

    @Override
    public CategoryDTO deleteCategory(Long Id) {
        Optional<Category> op_category=categoriesRepository.findById(Id);
        Category fixedcategory=op_category.
                orElseThrow(()->new ResourceNotFound("Category","categoryId",Id));
        categoriesRepository.delete(fixedcategory);
        CategoryDTO deletedDto=mapperMethods.toDto(fixedcategory);
        return deletedDto;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDto, Long categoryId) {
        Optional<Category>optional_category=categoriesRepository.findById(categoryId);
        Category savedCategory=optional_category.orElseThrow(()->new ResourceNotFound("Category","categoryId",categoryId));
        Category category=mapperMethods.toEntity(categoryDto);
        savedCategory.setCategoryName(category.getCategoryName());
        Category updatedCategory=categoriesRepository.save(savedCategory);
        CategoryDTO updtateCategoryDto=mapperMethods.toDto(updatedCategory);
        return updtateCategoryDto;


//        Optional<Category>optionalCategory=categories.stream().filter(c->c.getCategoryId().equals(categoryId))
//                .findFirst();

    }


}
