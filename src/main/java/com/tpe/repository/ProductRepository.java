package com.tpe.repository;

import com.tpe.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//     9-ProductRepository interface
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    //17-c
    boolean existsByProductName(String productName);
}
