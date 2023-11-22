package com.tpe.dto;

import com.tpe.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    @NotNull(message = "Product name can not be null")
    private String productName;

    @NotNull(message = "Brand can not be null")
    private String brand;

    @NotNull(message = "Price can not be null")
    private double price;

    public ProductDTO(Product product) {
        this.productName=product.getProductName();
        this.brand=product.getBrand();
        this.price=product.getPrice();
    }
}
