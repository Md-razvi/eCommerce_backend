package com.e_commerce.project.config;

import com.e_commerce.project.model.Category;
import com.e_commerce.project.model.Product;
import com.e_commerce.project.payload.CategoryDTO;
import com.e_commerce.project.payload.CategoryResponse;
import com.e_commerce.project.payload.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class MapperMethods {
    public CategoryDTO toDto(Category category){
        CategoryDTO categoryDTO=new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());
        return categoryDTO;
    }
    public Category toEntity(CategoryDTO categoryDTO){
        Category category=new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());
        return category;
    }
    public ProductDTO toDto(Product product){
        ProductDTO productDTO=new ProductDTO();

        productDTO.setProductId(product.getProductId());
        productDTO.setProductName(product.getProductName());
        productDTO.setDescription(product.getDescription());
        productDTO.setProductImage(product.getProductImage());
        productDTO.setPrice(product.getPrice());
        productDTO.setDiscount(product.getDiscount());
        double specialPrice = product.getPrice() *
                (1 - product.getDiscount() / 100);
        productDTO.setSpecialPrice(specialPrice);

        return productDTO;
    }
}
