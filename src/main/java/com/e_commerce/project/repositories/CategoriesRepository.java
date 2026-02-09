package com.e_commerce.project.repositories;

import com.e_commerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Category,Long> {
    Category findByCategoryName(String categoryName);
}
