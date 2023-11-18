package com.tpe.repository;

import com.tpe.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
//JpaRepository nasil bir interface ?Generic bir interface avantaji istedigimiz data tipleri ile kullanabiliyoruz
// bu Jpa iki tip data aliyor icine birincisinde sorgularimizi kullanirken birincisi
// sorgunda kullandigin data tipini belirtiyorsun ikincisi praymeriki olarak kullandigin data tipini isaretliyoruz yani Id data tipi Long
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    //customerservice klasinda yaptigimiz email surgulama yetrli olunduguna karar verildi
    //interfacmiz bir abraks klas tarafindan kontrol edilmesi gerekiyordu
    boolean existsByEmail(String email);

}
