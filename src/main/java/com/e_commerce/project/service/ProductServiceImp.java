package com.e_commerce.project.service;

import com.e_commerce.project.exception.ApiException;
import com.e_commerce.project.exception.ResourceNotFound;
import com.e_commerce.project.model.Category;
import com.e_commerce.project.model.Product;
import com.e_commerce.project.payload.ProductDTO;
import com.e_commerce.project.payload.ProductResponse;
import com.e_commerce.project.repositories.CategoriesRepository;
import com.e_commerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService{

    private CategoriesRepository categoryRepository;
    private ProductRepository productRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${project.image}")
    private String path;
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
        List<Product> list_1=category.getProductList();
        boolean isProductPresent=false;
        for(int i=0;i<list_1.size();i++){
            if(list_1.get(i).getProductName().equals(productDTO.getProductName())){
                isProductPresent=true;
                break;
            }
        }
        if(!isProductPresent) {
            productDTO.setProductImage("default.jpg");
            Product product = modelMapper.map(productDTO, Product.class);
            product.setCategory(category);
            double specialPrice = product.getPrice() * (1 - product.getDiscount() / 100);
            product.setSpecialPrice(specialPrice);
            Product savedProduct=productRepository.save(product);
            ProductDTO productDTO_1=modelMapper.map(savedProduct,ProductDTO.class);
            return productDTO_1;
        }else{
            throw new ApiException("The product already exists in Library");
        }


    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> pageProducts=productRepository.findAll(pageDetails);
        List<Product> products=pageProducts.getContent();
        List<ProductDTO>productList=products.stream().map(e->modelMapper.map(e,ProductDTO.class)).toList();
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productList);
        setPaginationDetails(productResponse,pageProducts);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFound("Category","categoryId",categoryId));
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> products=productRepository.findByCategory(category,pageDetails);
        List<Product> products1=products.getContent();
        List<ProductDTO>productList=products1.stream().map(e->modelMapper.map(e,ProductDTO.class)).toList();
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productList);
        setPaginationDetails(productResponse,products);
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyword(String s, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageDetails=PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> products=productRepository.findByProductNameContainingIgnoreCase(s,pageDetails);
        List<ProductDTO>productList=products.getContent().stream().map(e->modelMapper.map(e,ProductDTO.class)).toList();
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productList);
        setPaginationDetails(productResponse,products);
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
        String path="images/";
        String filename=fileService.uploadImage(path,image);
        product.setProductImage(filename);
        Product Updatedproduct=productRepository.save(product);
        return  modelMapper.map(Updatedproduct,ProductDTO.class);

    }
    @Override
    public void setPaginationDetails(ProductResponse productResponse,Page<?> pageProducts){
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setLastPage(pageProducts.isLast());
    }


}
