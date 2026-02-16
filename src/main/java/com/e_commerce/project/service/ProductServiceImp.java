package com.e_commerce.project.service;

import com.e_commerce.project.exception.ResourceNotFound;
import com.e_commerce.project.model.Category;
import com.e_commerce.project.model.Product;
import com.e_commerce.project.payload.ProductDTO;
import com.e_commerce.project.payload.ProductResponse;
import com.e_commerce.project.repositories.CategoriesRepository;
import com.e_commerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.e_commerce.project.config.MapperMethods;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImp implements ProductService{

    private CategoriesRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    public void setProductRepo(CategoriesRepository categoryRepository,
                               ProductRepository productRepository){
        this.categoryRepository=categoryRepository;
        this.productRepository=productRepository;
    }
    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {

        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFound("Category","categoryId",categoryId));


        productDTO.setProductImage("default.jpg");
        Product product=modelMapper.map(productDTO,Product.class);
        product.setCategory(category);
        double specialPrice = product.getPrice() * (1 - product.getDiscount() / 100);
        product.setSpecialPrice(specialPrice);
        Product savedProduct=productRepository.save(product);
        ProductDTO productDTO_1=modelMapper.map(savedProduct,ProductDTO.class);
        return productDTO_1;
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products=productRepository.findAll();
        List<ProductDTO>productList=products.stream().map(e->modelMapper.map(e,ProductDTO.class)).toList();
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productList);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFound("Category","categoryId",categoryId));
        List<Product> products=productRepository.findByCategory(category);
        List<ProductDTO>productList=products.stream().map(e->modelMapper.map(e,ProductDTO.class)).toList();
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productList);
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {
        List<Product> products=productRepository.findByProductNameContainingIgnoreCase(keyword);
        List<ProductDTO>productList=products.stream().map(e->modelMapper.map(e,ProductDTO.class)).toList();
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productList);
        return productResponse;

    }
    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product product=productRepository.
                findById(productId).orElseThrow(()->new ResourceNotFound("Product","ProductId",productId));
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        double specialPrice=product.getPrice() * (1 - product.getDiscount() / 100);
        product.setSpecialPrice(specialPrice);
        Product savedProduct=productRepository.save(product);
        ProductDTO product2=modelMapper.map(savedProduct,ProductDTO.class);
        return product2;
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFound("Product", "productId", productId));
        ProductDTO productDTO=modelMapper.map(product,ProductDTO.class);
        productRepository.delete(product);
        return productDTO;
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //Get Product from Db
        Product product=productRepository.findById(productId).
                orElseThrow(()->new ResourceNotFound("Product","productId",productId));
        String path="image/";
        String filename=uploadImage(path,image);
        product.setProductImage(filename);
        Product Updatedproduct=productRepository.save(product);
        return  modelMapper.map(Updatedproduct,ProductDTO.class);

    }

    private String uploadImage(String path, MultipartFile file) throws IOException {
        String orginalFileName= file.getOriginalFilename();
        String randomID= UUID.randomUUID().toString();
        String fileName=randomID.concat(orginalFileName.substring(orginalFileName.lastIndexOf(".")));
        String filePath=path+ File.separator+fileName;
        File folder=new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;


    }
}
