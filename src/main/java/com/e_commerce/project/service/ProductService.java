package com.e_commerce.project.service;

import com.e_commerce.project.model.Product;
import com.e_commerce.project.payload.ProductDTO;
import com.e_commerce.project.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService{
    ProductDTO addProduct(ProductDTO product,Long categoryId);
    ProductResponse getAllProducts();
    ProductResponse searchByCategory(Long categoryId);

    ProductResponse getProductsByKeyword(String keyword);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
