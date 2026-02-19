package com.e_commerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @NotBlank(message = "Fields cannot be empty")
    @Size(min=5 , message = "the word should be at least 5 characters")
    private String categoryName;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> productList;


}
