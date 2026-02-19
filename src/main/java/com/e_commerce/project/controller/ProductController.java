package com.e_commerce.project.controller;

import com.e_commerce.project.config.AppConstants;
import com.e_commerce.project.model.Product;
import com.e_commerce.project.payload.ProductDTO;
import com.e_commerce.project.payload.ProductResponse;
import com.e_commerce.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;
    ProductController(ProductService productService)
    {
        this.productService=productService;
    }
    @PostMapping("/admin/categories/{categoryId}/addProduct")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO product,
                                                 @PathVariable Long categoryId){
        ProductDTO productDTO=productService.addProduct(product,categoryId);
        return new ResponseEntity<>(productDTO,HttpStatus.CREATED);
    }
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse>getAllProducts(
            @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
            @RequestParam(name="sortBy",defaultValue = AppConstants.SORT_PRODUCT_BY,required = false)String sortBy,
            @RequestParam(name="sortOrder",defaultValue = AppConstants.SORT_ORDER,required = false)String sortOrder
    ){
        ProductResponse productResponse=productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
    @GetMapping("/public/{category_Id}/products")
    public ResponseEntity<ProductResponse>getProductsByCategory(@PathVariable Long category_Id,
                                                                @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                                @RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                                @RequestParam(name="sortBy",defaultValue = AppConstants.SORT_PRODUCT_BY,required = false)String sortBy,
                                                                @RequestParam(name="sortOrder",defaultValue = AppConstants.SORT_ORDER,required = false)String sortOrder){

        ProductResponse products=productService.searchByCategory(category_Id,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse>getProductsByKeyword(@PathVariable String keyword,
                                                               @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                               @RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                               @RequestParam(name="sortBy",defaultValue = AppConstants.SORT_PRODUCT_BY,required = false)String sortBy,
                                                               @RequestParam(name="sortOrder",defaultValue = AppConstants.SORT_ORDER,required = false)String sortOrder){

        ProductResponse products=productService.getProductsByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(products,HttpStatus.FOUND);
    }
    @PutMapping("/admin/products/{product_Id}")
    public ResponseEntity<ProductDTO>updateProduct(@RequestBody ProductDTO productDTO,@PathVariable Long product_Id ){
        ProductDTO updated_product=productService.updateProduct(productDTO,product_Id);
        return new ResponseEntity<>(updated_product,HttpStatus.OK);

    }
    @DeleteMapping("/admin/product/delete/{product_id}")
    public ResponseEntity<ProductDTO>deleteProduct(@PathVariable Long product_id)
    {
        ProductDTO deleted_product=productService.deleteProduct(product_id);
        return new ResponseEntity<>(deleted_product,HttpStatus.OK);
    }
    @PutMapping("/admin/products/{product_Id}/image")
    public ResponseEntity<ProductDTO>updateImage(@PathVariable Long product_Id, @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO product=productService.updateProductImage(product_Id,image);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
}
