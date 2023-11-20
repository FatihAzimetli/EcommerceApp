package com.tpe.dto;

import lombok.AccessLevel;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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


    @NotBlank(message = "Name is required!")
    private String name;


    @NotBlank(message = "Lastname is required!")
    private String  lastName;


    @NotBlank(message = "Email is required!")
    @Column(unique = true)
    @Email
    private String email;

    private String phone;
    // datalari gizlemek uygulamanin performansini artirir
}
