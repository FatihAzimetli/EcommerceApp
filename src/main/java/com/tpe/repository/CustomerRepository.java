package com.tpe.repository;

import com.tpe.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//     6-CustomerRepository interface
@Repository//optional
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    //13-c
    boolean existsByEmail(String email);//Spring tarafından derleme aşamasında implemente edilir.


    //22-c
    List<Customer> findByName(String name);

    //23-c
    List<Customer> findByNameAndLastName(String name, String lastName);


    //24-c
    //jpql
    //@Query("FROM Customer c WHERE c.name LIKE %:pWord%")
    @Query(value = "SELECT * FROM Customer c WHERE c.name LIKE %:pWord%",nativeQuery = true)//SQL
    List<Customer> findByNameLikeWord(@Param("pWord") String word);//ab


    //24-c:alternatif
    List<Customer> findByNameContaining(String word);
}
