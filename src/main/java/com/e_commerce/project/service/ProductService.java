package com.e_commerce.project.service;

import com.e_commerce.project.model.Product;
import com.e_commerce.project.payload.ProductDTO;
import com.e_commerce.project.payload.ProductResponse;

public interface ProductService{
    public ProductDTO addProduct(ProductDTO product,Long categoryId);
    public ProductResponse getAllProducts();
}
