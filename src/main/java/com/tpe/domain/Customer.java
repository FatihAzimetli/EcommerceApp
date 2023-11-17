package com.tpe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor //Parametreli conructer kullanilmadan tek tek setter edebiliriz
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //otomatik olarak generet edilecek buna generate anatasyonu yazilir
    @Setter(AccessLevel.NONE)//Seter metodu olmasin diyoruz
    private Long id;


    @NotBlank(message = "Name is required!")
    private String name;


    @NotBlank(message = "Lastname is required!")
    private String  lastName;


    @NotBlank(message = "Email is required!")
    @Column(unique = true)
    @Email
    private String email;

    private String phone;

    //Costemer ve order arasindaki iliski onetomany olacak
    // Iliskiler arasinda OneToMany yada ManyToOne iliskisi varsa
    // bu iliskiyi hangi taraftan takip etmeliyiz
    // Hangi tabloda Forinqi olusmasini isterdik ?? Many tarafinda olusmasini isteriz cünkü
    // one tarafinda olusturursak eger karsilikli olarak birden fazla deger girecegi icin
    // bir Forinky sutununda bu degerleri tutamiyoruz oyle olunca 3'üncü bir tablo olusturmamiz gerekiyordu
    // §'üncü bir tablo olusturmak yerine cift yönlü bir iliski kurmus isek
    // Burada tablo olusturmayip diger tarafta yani order tarafinda Forinky sutunu eklensin
    // buradaki Orderlarin degeri ise Order clasindaki costemer'e göre mep edilsin diye biliyorduk.
    //Orderlar icin bir ücüncü tablo olusturma burdaki orderlari map et
    //Nereden map et? order klasinda customer isminde bir field olacak bu fielde göre maple diyoruz
/*ayrica bir cüstomer sildigimizde bunu tabloda tutmamiza gerek yok
* bunun otomatik olarak gerceklesmesi icin bir müsteriyi silmis isek siparislerinide sil diyebilmek icin
*asamali silme islemini gerceklestire bilmek icin cocugu yetim birakma diye bilmek icin
* ikitane secenegimiz varde cascade entrybiyotunu casde type remove yapariz,
* yada orfferReMove  özelligini ekleyebiliriz .. Bunda ne fark var,
* eger siparisleri de sileceksek orfferREMOVE kullanmaliyiz*/
    @OneToMany(mappedBy = "customer",cascade = CascadeType.REMOVE)
    private Set<OrderItem> orders = new HashSet<>();//costumer siparis olustururken ayni siparisleri
    // tekrar tekrar ekliyemesin istyorsak ve girdigi siparisler farkli olsun istiyorsak
    // Set metodunu kullaniriz ve <Order Iten> kullansik ve HashSet ile karsiladik


}
