package com.e_commerce.project.controller;
import com.e_commerce.project.config.AppConstants;
import com.e_commerce.project.payload.CategoryDTO;
import com.e_commerce.project.payload.CategoryResponse;
import com.e_commerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class CategoryController {
    private final CategoryService categoryService;
     CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }
    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name="pageNumber", defaultValue =AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
            @RequestParam(name="sortBy",defaultValue = AppConstants.SORT_CATEGORIES_BY,required = false)String sortBy,
            @RequestParam(name="sortOrder",defaultValue = AppConstants.SORT_ORDER,required = false)String sortOrder ){
        CategoryResponse allCategories = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(allCategories,HttpStatus.OK);
    }
    @PostMapping("/api/admin/createCategories")
    public ResponseEntity<CategoryDTO> createCategories(@Valid @RequestBody CategoryDTO categoryDto){
        CategoryDTO saved=categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/deleteCategory/{categoryId}")
    public ResponseEntity<CategoryDTO> DeleteCategories(@PathVariable Long categoryId){

             CategoryDTO status = categoryService.deleteCategory(categoryId);
             return ResponseEntity.ok(status);

    }
    @PutMapping("/api/admin/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDTO>UpdateCategories(@Valid @RequestBody CategoryDTO categoryDto,@PathVariable Long categoryId){
             CategoryDTO savedCategory= categoryService.updateCategory(categoryDto,categoryId);
             return ResponseEntity.ok(savedCategory);

    }


}
