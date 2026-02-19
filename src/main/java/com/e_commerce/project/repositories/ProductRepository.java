package com.e_commerce.project.repositories;

import com.e_commerce.project.model.Category;
import com.e_commerce.project.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByCategory(Category category, Pageable pageDetails);
    Page<Product> findByProductNameContainingIgnoreCase(String keyword,Pageable pageDetails);

}
