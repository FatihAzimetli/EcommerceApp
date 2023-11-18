package com.tpe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter

public class OrderItem {
    @Id//otomatik generat edilelecek
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)//otomatik gneret olacagi icin stter edilmesin
    private Long id;


    @NotNull(message = "Quantity can not be null")
    private Integer quantity;//adet


    @NotNull(message = "Total price can not be null")
    @Setter(AccessLevel.NONE)//otomatik hesaplanacagi icin setter edilmesin
    private Double totalPrice;//adet*fiyat carpimi ile bunu hesapliyoruz

    @ManyToOne//Bir üründen cok kisi siparis verebilir ancak
    // bir kisi sadece 1 siparis veriyor yani pc alir ama monitör alamaz
    //Tabloda bir Forinky sutunu olacak ancak
    //Forinky sutununa not null girilmemesi icin
    // biz DB tarafindayiz normalde Collem anatsyonunu kullaniyorduk iliskileri kullanirken
    // özellestirilmis anatasyonumuz JoinColumn kullanacagiz
    @JoinColumn(nullable = false)//Forinky sutunu icin bunu kullandik
    @NotNull(message = "Product can not be null")
    private Product product;//fiyat


    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Customer can not be null")
    private Customer customer;

    @PrePersist//totalpreis metodunu cagiracak olan metoht @PrePersist dir
    @PreUpdate//update etmeden prepersist tekrar cagir ve tabloya bu sekilde kaydet.
    public void countTotalPrice(){//Bu countTotalPrice aslinda bir setter metodudur ancak kendi istegimiz sekilde set ediyoruz.
        this.totalPrice=this.product.getPrice()*this.quantity;//Bu metod ile fiyat otomatik hesaplanacak
    }
//encuplulation field ve datalara disardan ulasmayi engellemektir.
    // public void countTotalPrice(){//Bu countTotalPrice aslinda bir setter metodudur ancak kendi istegimiz sekilde set ediyoruz.
    //        this.totalPrice=this.product.getPrice()*this.quantity;//Bu metod ile fiyat otomatik hesaplanacak
    //    }

}
