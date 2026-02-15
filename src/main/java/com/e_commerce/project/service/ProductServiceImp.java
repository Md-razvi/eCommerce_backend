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

import java.util.List;

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
}
