package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
import com.tpe.exeption.ConflictException;
import com.tpe.exeption.ResourceNotFoundException;
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
    //5.1.a dogrudan id ile customer objesinin kendisini getirme methodu asagida yaptigimizi aldik
    // ve bunun body yapisturdik. asagida orasini yoruma aldim "" tirnak isareti ile isaretledim geriye dönüsünü
    // yazdik Customer customer = bundan sonrasi icin optional ile sarmalanmis deger döndürüyor optional ile
    // sarmalanmis deger eger bu id sahip olan customer tablomuzda var ise dönen customer objesini Customer
    // objesine atiyor eger tabloda bu id'ye sahip olan bir customer yoksa bu durumda null deger döndürmek yerine
    // bu null degeri optional ile sarmalayarak gönderiyor ve null gelme ihtimalini bu method ile hendil et diyor
    // ve id geger dolu ise o zaman bir eksepsin firlat .orElseTHrow () "Kaynak bulunamadi hatasi gönderecek
    //.orElseTHrow() bu metod bir funtional bir suplaydir funtional bir interface objesi konkirt bir klas
    // olusturmadan lamda expertional ile suplayer interface icindeki tek birtane olan metodu overide edebiliyorduk.
    // bu id fronted görüyor
    public Customer getCustomerById(Long id){
      Customer customer =  customerRepository.findById(id).
              orElseThrow(()->new ResourceNotFoundException("Customer is not found by id: "+id));
      return customer; // bunu sonra degistirecegiz
      //kullanici 404 not found da bu mesaji görecek
    }

//4.1.a
    //customer repotory getirmek icin ne yapiyorduk.? customerRepository. bununicin findById gibi bir metod var.
    //customerRepository.findById(identity); bu sekilde yazdik bu oldugu
    // icin buraya SELECT * FROM Customer WHERE .... seklinde bir sorgu yazmamiza gerek yok. findById metodundan dolayi
    // findById üzerine geldigimizde publick abstract java.util.Optional<T>findById(ID id) bu opsiyonel sarmalsmis
    // bir customer döndürüyor ancak biz metodutumzda customer döndürmeyecegiz bu islem icin ayri bir
    // metod olusturulacak cünkü geriye costomer döndüren bir metoda ihtiyacimiz olabilir 5.1.a
//6.1.a id ile Customer DTO getirme
    //bunun icin getCustomerById(identity) verdigimizde geriye bize Customer tipinde bulunan Customer foundCustomer ' a
    // dönecek eger Customer bulunamazsa bu durumda tekrar hendil etmeye gerek yok cünkü onu yukarida
    // 5.1.a. daki getCustomerById de yapmis olduk burada customerdto obje olusturup bununla dönüs yapabiliriz
    // CustomerDTO customerDTO = new CustomerDTO(); bu durumda burda her deger null dir name,
    // lastname gibi degerleri set etmemiz gerekiyor acak bunlari
    /* Customer foundCustomer = getCustomerById(identity);
        CustomerDTO customerDTO = new CustomerDTO();*/
    //tek tek set etmek yerine [new CustomerDTO();] bu conracter parametreli bir constructer olsa idi [ foundCustomer]
    // icindeki bilgileri alip buraya verdigimizde CustomerDTO customerDTO = new CustomerDTO(foundCustomer);
    // verseydik ve dogrudan bir DTO objesi olustursaydik bunu nasil yapabilirdik tek tek set etmek yerine parametreli
    // bir cunstroctcerem olsa cüstomeren fiellerini alsam ve customerDTO tasisam nasil
    // tasiyacagim.? 7.1.a customerDto klasina gittik
    // buraya geri döndük costemerDto baktik ve //allArgsConsructer kullanirsak name, lastname, email, phone
    // parametrede vermemiz gerekiyor dedik ve Alttaki new CustomerDTO(); parentez icini doldurduk
    // CustomerDTO customerDTO = new CustomerDTO(foundCustomer.getName(),); uzun uzun buraya kod yazmak
    // gerekiyor bunun yerine daha pratik yöntem ....tekrar CustomerDTO sayfasina gittik. 8.1.a.
    //CustomerDTO customerDTO = new CustomerDTO(foundCustomer); olusan veraible buraya yazdik
    public CustomerDTO getCustomerDTO(Long identity) {
        Customer foundCustomer = getCustomerById(identity);
        CustomerDTO customerDTO = new CustomerDTO(foundCustomer);
       // ""customerRepository.findById(identity);"" buradan aldik yukariya yapistirdik icindeki identity silip id yaptik
    }
}
