package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.exeption.ConflictException;
import com.tpe.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
      //existsByEmail existsById metodunun türetilmis halidir tabloda eslesen bir email varsa
        // isExists true döndürecek bu durumda if kosulunu yaziyoruz true döndürürse ne olacak ve tablomuzda
        // email varsa yapmamiz gereken conflit exception firlatacagiz neden bu excepsin filatacagiz
        if(isExists){
            //neden castem bi exeption firlatiyoruz? okunabilirligi artirmak icin. Ancak burada
            // bir cakisma exeption firlatirsak ConflictException bu mesaji görünce burada bir
            // cakisma oldu anlasilacak detaya bakmaya gerek kalmiyacak bu uygulamanin okunurlugu artirir
            throw new ConflictException("Customer already exists by email:"+ customer.getEmail()); //buradaki
            // ConflictException nerde hendel edilecek.? burada iki secenek var restapy icinde yada
            // fronted tarafina birakip fronted tarafinda yapabiliriz
            //ConflictException morextion create class 'ConflictException' ile great ediyoruz
            // ancak servis katmani class olustur olarak geliyor servisi siliyor exception
            // oldugu icin exception yapiyoruz
        }
        // eger if buloguna girmez ise kayit etmemiz gerekiyor
        customerRepository.save(customer);

    }
    //2.7.a)getAll omadigi icin ona serviste olustur dedik
    //2.8.a) burada bunu retörn etmemiz gerekiyor
    //2.9.a)  return customerRepository.findAll(); ve findAll kizmadi  findAll bir sorgu
    // yazdigimizda customer listesi dönecek kizmamam sebebi Pg repositoride findAll metodu var.
    // ne yapiyor "From Customer" tablosundan bizim icin zaten sorguyu yapiyor
    //2.10.a) bunu frontedde test edecegiz testimizi sahte frontedde yapacagiz postmen uygulamasi ile


    public List<Customer> getAll() {
        return customerRepository.findAll();//Fromm customer tablosundan kayitlari getir demis olduk
    }
}
