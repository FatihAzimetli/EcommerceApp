package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//3
@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "Quantity can not be null")
    private Integer quantity;

    @NotNull(message = "Total price can not be null")
    @Setter(AccessLevel.NONE)
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Product can not be null")
    private Product product;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Customer can not be null")
    @JsonIgnore
    private Customer customer;

    @PrePersist
    @PreUpdate
    public void countTotalPrice(){
        this.totalPrice=this.product.getPrice()*this.quantity;
    }

}