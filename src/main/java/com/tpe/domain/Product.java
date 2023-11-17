package com.tpe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity//entity kullanildiginda @id anatasyonu ile bir emtity belirtiriz ve Id otomatik generation edilsin denilmis
@Getter //klas seviyesindedir
@Setter //fieldlerin medotlarini klas seviyesinde olusturur
@NoArgsConstructor//lombok kutuphanesinden parametresiz contructer metodu
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)// bu durumda @id sonra bu eklenir ve starejisi belirlenir
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Product name is required!")//space,null,empty olmamasi icin bu metod kullanilir
    @Column(unique = true)//productName uniq olmasi icin
    private String productName;


    @NotBlank(message = "Product brand is required!")//space,null,empty olmamasi icin bu metod kullanilir
    private String brand;


    @NotNull(message = "Price can not be null!")//sadece null olmasin istenirse bu metod kullanilir
    private Double price;

//burada parametreli contructer olusturulur setter metodlari yerine


    public Product(String productName, String brand, Double price) {
        this.productName = productName;
        this.brand = brand;
        this.price = price;
        //Buraya parametreli contructer olusturuldugu icin parametresiz contructer ezildi
        // bu durumda HBnet bizden parametresiz contructerda ister
        // bunu manual yazmak yerine clas seviyesinde @NoArgsConstructor metodunu ekleriz
    }

    //Buraya getter ve setter metodlarina artik gerek yok bunun icin lombo kutuphanesindeki kod kullanilacak yukarida


}
