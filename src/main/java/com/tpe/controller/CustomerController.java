package com.tpe.controller;


import com.tpe.domain.Customer;
import com.tpe.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@Controller// springde ögretilen ilk bu anatasyon konmali bu klasta requestler handel edilecek
// Ancak daha özellestirilmis hali ResController metodunu koyacagiz
@RestController//daha özellestirilmis halidir burada bir ResApy gelistiriyoruz
// requesleri bunun karsilayip risponslari olusturacagiz
//ilk entpointler custemers ile basliyor http://local/host8080/customer/save +POST + body
//POST tabloda degisiklik istegidir //kullanicinin Id otomatik genaret edilecek
//ama musterinin cok bilgisi name lastname phone e-mail gibi bilgileri var bunlar bize
//jhsob formatinda body olarak gelecek
// baslangictaki entpoint baktik 11 tane birinci entpoint ile basliyoruz
// 1-customer olusturma/kaydetme
@RequestMapping("/customers")//requestler customer ile basladigi icin ilk bu request map lenecek
@RequiredArgsConstructor //final ile isaretli parametre contructer lari enjekte etti
public class CustomerController {
    //costemer servis kontrolleri yapacak
    //costemerkontroller bir biindir custemer custemer kontrollü kapsiyacak
    //custemer kontroller sipring icindeki bir biin dir gerektiginde enjekte edilmesi icin privat final yapiyoruz
    //ejekte edildikten sonra degistirilmemesi icin final edilir. final yapilan bir servis set edilmek istenir
    //hatanin önüne gecmek icin injecsin yapilir. ileri ypi icin parametreli cosntracter tavsiye edilir
    //bu yapildiginda hata gider @RequiredArgsConstructor bu zorunlu argumanlari icerir
    //bu @RequiredArgsConstructor parametresi custemerService olmus olur ve hata giderilmis olur

   private final CustomerService customerService ; //!!-heryerde ayni obje objenin kullanilmasi icin sadece companint ile
    // !!-isaretliyoruz  CostemerService costemerService= newCostemurService gibi bir obje olusturmuyoruz
    // !!- newleme maliyetli ve geciktiricidir
   /* 3- Spring ile control bir compenent ile--
    * bitiriyoruz newleme maliyetlidir enjekte etmek icin basina privat getiriyoruz--
    *-- enjekte edildikten sonra degistirilemesin final olsun istiyoruz  final
    *oldugunda hata verir bu durumda hemen set etmemizi istiyor finel yapmadan otoweiser anatasyonunuda
    *kullanabilirdik ancak encok güvenlik bakim ve gelistirme acisindan tavsiye
     *edilen Contructer injeksiyon tavsiye ediliyor lombok kutubanedeki (RequestArgsContructer)
     * zorunlu argumanlari iceren consructer dir yukariya ekledik */
    //!!!customer kaydetme : http://localhost:8080/customers/save + POST + body
    //bir degisiklik olduguna göre tabloya kaydetme istegi olduguna göre bu istek bir GTT metodu ile gelecek (POST)
    //Tabloda degisiklik yapma istegi oldugu icin ve bu istegi hendil etmek icin ama
    // müsterinin cok bilgis var last neam email phone gibi bu bilgileri almamiz gerekiyor.
    // SpringMvc bir form ile aliyorduk burada bir form yok
    // Fronted bunu bir form ile alacak bize frontedden bu nasil gelecek?
    // bu http bilesenlerin body'si araciligi ile jhson formatinda gelecek
    //burada 1- sirada eger email daha önce kullanilmissa hata uyarisi verecek
    // bu hata kendimizin olusturdugu (conflictexception) olacak (Cakisma hatasi)
    //post metodu ile gelecegi icin postMapping ile karsilayacagiz
    //basarili bir sekilde kayit tamamlaninca fronted tarafindan 201 kodu gönderilir
    //Kayit etmek icin DB baglanmamiz gerekiyor ancak kayit icin Repository den baglaniyoruz
    //buradan direk iletisime gecermi hayir araya bir kontrolcu daha koyoyoruz
    // bu kontrol önce servisten gececek sonra kontrole gelecek
    //!!!customer kaydetme : http://localhost:8080/customers/save + POST + body
    // !!!!bilgileri jhonsen medotu ile kullanicidan alacagiz ancak daha önce email girilmisse hata bilgisi verecegiz
    //HttpStatus.CREATED bu inam olusturmaktir
    //201 kodu fronted tarafina gönderilmis olur

    @PostMapping("/save")
    public ResponseEntity<String> createCustomer(@Valid @RequestBody Customer customer){
        //DB repository de baglaniyoruz ve araya bir kontrolör koyuyoruz
        //repositoriy ile kontroler bire bir iletisime gecermi.?
customerService.saveCustomer(customer);
        return new ResponseEntity<>("Customer is saved successfully", HttpStatus.CREATED);//201
    }
    //2..1.a)ikinci endpoint tüm customerlari getirme islemi--> http://localhost:8080/customers + GET
    //2.2.a) burada kullanicidan herhangi bir data almamiza gerek yok sadece customerlari gösterme islemi yapacagiz
    //2.3.a) @GetMapping () cagiriyoruz ve pet'ine yeni bir end point eklemeye gerek yok
    //2.4.a) ResponseEntity<List> burada customer listesi dönecek
    //2.5.a)ok kodu 200'dür    return new ResponseEntity<>(customerList,HttpStatus.OK);//200 bunun bir altarnativi var
    //2.6.a)return ResponseEntity.ok(customerList);//200 dönmesini istedigimizin body () yazmamiz yeterli
    //2.7.a)getAll omadigi icin ona serviste olustur dedik
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customerList = customerService.getAll();
        //return new ResponseEntity<>(customerList,HttpStatus.OK);//200
        return ResponseEntity.ok(customerList);//200
    }

}
