package com.e_commerce.project.controller;

import com.e_commerce.project.model.Product;
import com.e_commerce.project.payload.ProductDTO;
import com.e_commerce.project.payload.ProductResponse;
import com.e_commerce.project.service.ProductService;
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
    @GetMapping("/public/{category_Id}/products")
    public ResponseEntity<ProductResponse>getProductsByCategory(@PathVariable Long category_Id){
        ProductResponse products=productService.searchByCategory(category_Id);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse>getProductsByKeyword(@PathVariable String keyword){
        ProductResponse products=productService.getProductsByKeyword(keyword);
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
