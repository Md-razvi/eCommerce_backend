package com.e_commerce.project.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    @NotBlank(message = "Please add the name of the product")
    @Size(min=4)
    private String productName;
    private String description;
    @NotBlank(message = "image could not be null please add it ")
    private String productImage;
    @NotNull(message = "The quantity is empty")
    private Integer quantity;
    @NotNull(message = "Please add price")
    private Double price;
    @NotNull(message = "Add the discount percentage")
    private Double discount;

    private Double specialPrice;
}
