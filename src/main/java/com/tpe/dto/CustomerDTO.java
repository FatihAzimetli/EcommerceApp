package com.tpe.dto;



import com.tpe.domain.Customer;
import lombok.Getter;
import lombok.Setter;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
@Getter
@Setter
public class CustomerDTO {
    // 3.1.a. costemer DTO icinde neler olmali domain icindeki kod temalarini aynen aldik yani costemer f
    // iledlerini aldik id, name,last name, emai, phone. üzerinde düzenleme yapacagiz burada görünmesini
    // istemediklerimizi siliyoruz anacak ben onlari yoruma cektim
   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //otomatik olarak generet edilecek buna generate anatasyonu yazilir
    @Setter(AccessLevel.NONE)//Seter metodu olmasin diyoruz
    private Long id;*/

    // eger baska bir istek örnegin name ve last name istendi baska
    // bir DTO klasi yapilir ve icinde sadec name, lastname olur

    //mesela kullaniciya update islemi icin mesela name, lastname izin vermiyor ama tlf izin veriyor olabiliriz
    // bu durumda kullanicidan diger bilgileri yenileme istemeden tlf no übdate yönlendiririz
    // buray @entity anatasyonunu ekleriz buradaki @notBlank anatasyonu kullanicidan bilgi alirken kullaniliyordu
    // @Email yine kullanicidan bilgi alirken email formatinda oömasini söylüyordu @Column anatasyonu DB kayit
    // esnasinda emil sutununda email in data eklenmesi aninda bu emailin unuq olmasini sagliyordu
    // biz burada klainte bilgi vermek icin DTO olusturduk simdi gereksiz olanlaro kaldiracagiz
/* @NotBlank(message = "Name is required!")
    private String name;


    @NotBlank(message = "Lastname is required!")
    private String  lastName;


    @NotBlank(message = "Email is required!")
    @Column(unique = true) bunun DB ve reposytory ile alakasi yok sildik 1.
    @Email
    private String email;

    private String phone;*/
    // bu fieldlere ulasmak icin getter ve setter anatasyonlarini kullandik.
    //simdilik @NoArg ve @AllArg kullanmadik ihtiyac olunca dönüp ekliyecegiz

     //7.1.a burada parametreli contructer olusturacagiz
    //allArgsConsructer kullanirsak name, lastname, email, phone parametrede vermemiz gerekiyor
    @NotBlank(message = "Name is required!")
    private String name;


    @NotBlank(message = "Lastname is required!")
    private String  lastName;


    @NotBlank(message = "Email is required!")

    @Email
    private String email;

    private String phone;
    // datalari gizlemek uygulamanin performansini artirir
    //8.1.a AllArg ve NoArg costructer kullanmak yerine kendimiz bir cunstructer yazmaya basladik.Burada yine
    // parametreli contructer istiyoruz ama parametresindeki degerleri buradan tek tek almasin bu degerleri elimizde
    // bir Customer customer objesi var o degerleri burdan almak istiyoruz public CustomerDTO(Customer customer)
    // deger aldiginda bu klastaki name degerini su sekilde set et diyoruz parametredeki customer'in name
    // ile degistir diyoruz yani gidip servis klasinda yapmamiz yerine burada degistir dedik CostemerService klasina
    // simdi dogrudan foundCustomer verdigimizde
    public CustomerDTO(Customer customer){
       this.name = customer.getName();
       this.lastName=customer.getLastName();
       this.email=customer.getEmail();
       this.phone=customer.getPhone();
    }
}
