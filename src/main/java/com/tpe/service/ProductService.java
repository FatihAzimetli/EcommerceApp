package com.tpe.service;

import com.tpe.domain.Product;
import com.tpe.dto.ProductDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.exeption.ConflictException;
import com.tpe.exeption.ResourceNotFoundException;
import com.tpe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// 8-ProductService Class
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //17-b
    public void addProduct(ProductDTO productDTO) {
        //17-c
        boolean isExists=productRepository.existsByProductName(productDTO.getProductName());
        if(isExists){
            throw new ConflictException("Product already exists by name"+productDTO.getProductName());
        }
        Product product=new Product(productDTO.getProductName(), productDTO.getBrand(), productDTO.getPrice());
        productRepository.save(product);
    }

    //18-b
    public List<Product> getAllProduct() {

        //List<Product> ---> List<ProductDTO> :practice

        return productRepository.findAll();
    }

    //19-b
    public Product getProductById(Long id) {
        return productRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Product not found by id: "+id));
    }

    //19-c
    public ProductDTO getProductDtoById(Long id) {

        Product product=getProductById(id);
        ProductDTO productDTO=new ProductDTO(product);
        return productDTO;
    }

    //26-b
    public Page<Product> getAllProductByPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}
