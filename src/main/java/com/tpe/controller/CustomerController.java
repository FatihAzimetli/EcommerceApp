package com.tpe.controller;


import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
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

// buraya postmen bilgiler doldurduk iki kullanici adi tlf ve email girildi

    /*    "id": 1,
        "name": "Jack",
        "lastName": "Sparrow",
        "email": "jack@mail.com",
        "phone": "123456789",
        "orders": []


        "id": 2,
        "name": "Harry",
        "lastName": "Potter",
        "email": "harry@mail.com",
        "phone": "123456788",
        "orders": []*/


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
//2.10 Id ile tek bir costemer getirme --> http://localhost:8080/customers/1 + GET
    // id tabloda yoksa hata firlatir .(resourceNotFoundException)
    // burada hangi hhtp metodu olacak soruldu. Burda customerlari getirip gösterme olacagi icin get metodu olacak
    // bu durumda GetMapping ile baslanir ve geriye ResponseEnttity döndürülür ve icinde  customer olur  ve
    // ismide getCustomer olsun seklinde metodu tanimlariz
    @GetMapping("/{id}")
public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long identity){
        CustomerDTO customerDTO  =customerService.getCustomerDTO(identity);
        return ResponseEntity.ok(customerDTO);//200 göndermis olacagiz

    }

    // soru bu sekilde getmapping yazarsam ne olur.? yukaridaki kod ile asagidaki kod ayni oldu bu
    // durumda cakisacak karasizlik olur ve hata firlatir bu durmda GetNapping yerine
    // PostMapping kullanirsak ayni http bölümünü cagirirken karisiklik olmaz burada
    // requestler tanimlanirken cakismamasina dikkat etmemiz gerekiyorr urada Getmapping kullanagiz
    // http://localhost:8080/customers/1  burada customerdan sonra /1 var ancak pet icini dolduracagiz biz
    // kullanicidan buradaki /1 json body disinda burada bit petverabil ile yada query ile bilgi alabiliriz
    // burada /1 yani bir pet  parametresi yada petvaraible kullanilmis  burda önce pet varible sonra
    // bir body icine degisken adini yazariz @GetMapping("/{id}") yani diyoruzki customerdan gelen
    // degeri id icine ata peki bu degiskeni kullanabilmemiz icin (@PathVariable Long id) yani pat g
    // elen degeri long icindeki id degere ata demis olduk (@PathVariable("id") Long id) yani pat
    // vaible icindeki id oku ve Long degerindeki degiskene ata diyoruz ancak burada isim kullanmak zorunda
    // degiliz  @GetMapping("/{id}") buradaki id ile asagidaki id ayni ve asagiya identity bile yazabiliriz sonucu degistirmez
    //public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) iste bu sekilded oluyor  @GetMapping("/{id}")
    //(@PathVariable Long identity) ama petvraiblde id=1 ve pet vraibgeda jkk gibi bir deger
    // varsa yani su sekilde http://local/host8080/customer/1/jkk +Get var ise o zaman pat
    // veraiblelain isimlerini kullanmamiz gerekiyor bizde birtane var dogrudan pat vraibele daki "{id}" i okuyacak
    // ancak yazarsak okunabilirligi artmis olur ancak burada ikinci bir sorun var. sorun nedir ??????
    // kullanici bizden id=1 olan customer istemis henüz burada otorasin otoreshin gerceklestirmedik ama
    // bu sorguyu yapan bir yöneticide olabilir dogrudan son kullanicida olabilir....???? bu durumda herhangi bir
    // kullaniciya customer'in örnegin id bilgisini göstermek istermiyiz.? bu durum devoleperle ilgili bir konu
    // kesinlikle son kullaniciya id bilgisini göstermek istemeyiz bu bilgileri admin ise gösteririz dB ile
    // iletisime gecen repo katmanidir kullanici ile dogrudan bu katmanlarda iletisime gecirmiyoruz araya bir
    // köprü daha koyuyoruz yani servis katmani amacimiz kontrolleri saglamak clainttan aldigimiz her bilgiyi
    // dogrudan DB göndermiyoruz bazi kosullari kontrol etmemiz gerekiyor kullanicidan gelen bilgilerin icine
    // bakmaliyiz ve bir kismini secerek DB gecirmeliyiz bazilarini iptal edebilir yada DB getirdigimiz bilgiyi
    // dogrudan kullaniciya aktarmak istemiyebiliriz cünkü DB bizim gizli hazinemizdir örnek customer burda "id"
    // göstermek istemiyoruz bu durumda yapmamiz gereken kullanicidan sadece istedigimiz bilgileri almak ve
    // kullaniciya sadece istedigi bilgileri döndürmek icin özel objeler olustururuz bu objeler DB ile
    // kullanici arasinda tranfer etmek icin kullanacagiz bunlara genel ismi ile "DTO" deyta Tranfer object
    // diyoruz bunlar DB ile kullanici arasinda tasidigimiz kullanilabilir objelerdir.
    // Gecici okunabilir ancak üzerine yazilamaz objelerdir. Bunun icin Customer obseinde
    // clainte göstereceklerimiz icin birtane özel obje tanimliyoruz Customer icin DTO olusturuyoruz
    // bu durumda mevcut pakeglar disinda birtane daha pakeg ihtiyacimiz var com.tpe üzerine gelip new pakej diyoruz
    // ve pakeg adi dto yaziyoruz ve klasimizi olusturuyoruz. public class CustomerDTO sonrasi 3.1.a ile basladim.
    //Customer DTO taniladik ve buraya döndük ve artik geriye Customer degil CustomerDTO dönecek
    //public ResponseEntity<Customer> getCustomer(@PathVariable Long identity)
    // yerin public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long identity) olacak
    // kolay kismi return yazdik  return ResponseEntity.ok(customerDTO);//220 göndermis olacagiz burda
    // customer döndügünde basarili mesaji dönecek ResponseEntity.ok metodunu kullandik ve dönecek olan 200 statü kodudur
    // Customer'i nasil bulacagiz servisimizin eger getCustemerDTO metodu olsaydi ve bu metoda
    // id i verdigimizde (identity) geriye bana CustomerDTO tipinde customerDTO döndürse idi bu customer DTO cevap
    // olarak icinde id (identity)olmayan bilgiyi fronted tarafina gönderdik Fronted de cevabi kullaniciya gönderdi
    /*@GetMapping("/{id}")
public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long identity){
        CustomerDTO customerDTO  =customerService.getCustomerDTO(identity);
        return ResponseEntity.ok(customerDTO);*/
    //Yani olay bu Ancak CustomerDTO customerDTO  =customerService."//getCustomerDTO//"(identity); kisim kizardi
    // üzerine geldik ve serviste bu metodu olustura tikladik. ve otomatik olarak servise yöneldik.
    // seviste olusum  public CustomerDTO getCustomerDTO(Long identity) {
    //    } bu sekilde oldu burda //4.1.a olarak nota serviste devam ediyorum.
}
