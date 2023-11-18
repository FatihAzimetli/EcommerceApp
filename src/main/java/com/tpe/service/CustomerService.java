package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
//bunu otomatik olusturduk
@Service //buradaki standar biin @Service dir
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    //final olmasi bir defa enjekte ediliyor ve daha sonra degistirilemiyor
    /*Customer kaydetmek icin kosulumuz var custemer icine gelen lastname ve email var
    * email bir identifayer belirleyici olarak kullanilacak denildi bu durumda email uniq olacak*/

    public void saveCustomer(Customer customer) {
        /*ama önce servis katmaninda bu durumu kontrol edecegiz bu custemerin emaili tabloda varmi.?
        bunu nasil kontrol edebiliriz bunu tabloda varmi diye bir sorgu yazacagiz*/
        /*eexistsByEmail tabloda araya bilirsin veya customerRepository.existsById()
         (bu metodu sildik Ve costum repository klasina gittik
        görede arayabilirsin buraya baska degerlde yazip aratabiliriz*/
      boolean  isExists =customerRepository.existsByEmail(customer.getEmail());

    }
}
