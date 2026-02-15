package com.e_commerce.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long productId;
    private String productName;
    private String description;
    private Integer quantity;
    private Double price;
    private Double specialPrice;
    private String productImage;
    private Double discount;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}
