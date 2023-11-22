package com.tpe.controller;

import com.tpe.domain.Product;
import com.tpe.dto.ProductDTO;
import com.tpe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//     7-ProductController Class
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //17-a)ürün oluşturma/ekleme->http://localhost:8080/products/add + JSON BODY + POST

    /*
    {
   "productName":"keyboardLGTCH99",
   "brand":"logitech",
   "price":500
    }
     */
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDTO productDTO){
        productService.addProduct(productDTO);
        return new ResponseEntity<>("Product is added successfully", HttpStatus.CREATED);//201
    }


    //18-a)-Tüm productları getirme->http://localhost:8080/products
    @GetMapping
    public ResponseEntity<List<Product>> allProducts(){
        List<Product> products=productService.getAllProduct();
        return ResponseEntity.ok(products);//200
    }

    //19-a)-Id ile product getirme->http://localhost:8080/products/5
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id){
        ProductDTO productDto=productService.getProductDtoById(id);
        return ResponseEntity.ok(productDto);
    }

    //26-a
    @GetMapping("/page")
    public ResponseEntity<Page<Product>> getAllProductsByPage(@RequestParam(required = false,value = "page",defaultValue ="0" ) int page,//hangi sayfa
                                                              @RequestParam("size") int size,//her sayfada kaç adet
                                                              @RequestParam("sort") String prop,//sıralama fieldı
                                                              @RequestParam("direction") Sort.Direction direction){//ASC,DESC
        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Product> productsPage=productService.getAllProductByPage(pageable);
        return ResponseEntity.ok(productsPage);
    }

}