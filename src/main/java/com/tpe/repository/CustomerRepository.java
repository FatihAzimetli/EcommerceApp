package com.tpe.repository;

import com.tpe.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository nasil bir interface ?Generic bir interface avantaji istedigimiz data tipleri ile kullanabiliyoruz
// bu Jpa iki tip data aliyor icine birincisinde sorgularimizi kullanirken birincisi
// sorgunda kullandigin data tipini belirtiyorsun ikincisi praymeriki olarak kullandigin data tipini isaretliyoruz yani Id data tipi Long
@Repository //opsiyoneldir yazilmasada oluyor
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    //customerservice klasinda yaptigimiz email surgulama yetrli olunduguna karar verildi
    //interfacmiz bir abraks klas tarafindan kontrol edilmesi gerekiyordu
    boolean existsByEmail(String email);//spring tarafindan derleme esnasinda implement edilir
    //Cok istiani durumda JPAREPOSITORY üretilen metod yeterli gelmezse bu durumda conkrit klas olusturmamiz gerekebilir
    //Bu klasin (bunun gibi klaslar) servis klasina enjekte edilmesi gerekiyor bunun icin
    // CustemerRepository'den bir biin olusturmak gerekiyor ancak CustomerRepository den biin olusturmak icin
    // normalde ropsotory klaslarda bir biin anatasyonu kullaniyorduk @Repository anatasyonunu kullanmak zorunlu
    // degil okunabilirligi artikmak adina bu @Repository ekliyoruz

}
