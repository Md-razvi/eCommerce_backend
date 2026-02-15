package com.e_commerce.project.controller;

import com.e_commerce.project.model.Product;
import com.e_commerce.project.payload.ProductDTO;
import com.e_commerce.project.payload.ProductResponse;
import com.e_commerce.project.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;
    ProductController(ProductService productService)
    {
        this.productService=productService;
    }
    @PostMapping("/admin/categories/{categoryId}/addProduct")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO product,
                                                 @PathVariable Long categoryId){
        ProductDTO productDTO=productService.addProduct(product,categoryId);
        return new ResponseEntity<>(productDTO,HttpStatus.CREATED);
    }
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse>getAllProducts(){
        ProductResponse productResponse=productService.getAllProducts();
        return new ResponseEntity<>(productResponse,HttpStatus.OK);

    }
}
