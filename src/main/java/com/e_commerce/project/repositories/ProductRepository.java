package com.e_commerce.project.repositories;

import com.e_commerce.project.model.Category;
import com.e_commerce.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    public List<Product>findByCategory(Category category);
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
