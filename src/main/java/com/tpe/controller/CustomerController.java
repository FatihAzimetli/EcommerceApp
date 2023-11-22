package com.tpe.controller;


import com.tpe.domain.Customer;
import com.tpe.domain.OrderItem;
import com.tpe.dto.CustomerDTO;
import com.tpe.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

//4-CustomerController Class

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;//constructor injection:autowired zorunlu değil


    //13-a-customer kaydetme:http://localhost:8080/customers/save + POST + JSON body
    //email daha önce kullanılmışsa hata fırlatır.(ConflictException)

//    {
//        "name":"Jack",
//            "lastName":"Sparrow",
//            "email":"jack@mail.com",
//            "phone":"123456789"
//    }

    @PostMapping("/save")
    public ResponseEntity<String> createCustomer(@Valid @RequestBody Customer customer){

        customerService.saveCustomer(customer);

        return new ResponseEntity<>("Customer is saved successfully", HttpStatus.CREATED);//201
    }

    //14-a-Tüm customerları getirme -> http://localhost:8080/customers + GET
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        List<CustomerDTO> customerList=customerService.getAll();
        // return new ResponseEntity<>(customerList,HttpStatus.OK);//200
        return ResponseEntity.ok(customerList);//200
    }

    //15-a-Id ile tek bir customer getirme -> http://localhost:8080/customers/1 + GET
    //id tabloda yoksa hata fırlatır.(ResourceNotFoundException)
    //15-b-CustomerDTO tanımlama
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id") Long identity){

        CustomerDTO customerDTO=customerService.getCustomerDTO(identity);

        return ResponseEntity.ok(customerDTO);//200
    }

    //ÖDEV: id ile customer getirme: http://localhost:8080/customers/get?id=1 + GET

    //16-a-id ile customer silme http://localhost:8080/customers/custom?id=1 + DELETE
    @DeleteMapping("/custom")
    public ResponseEntity<String> deleteCustomer(@RequestParam("id") Long id){

        customerService.deleteCustomerById(id);

        return ResponseEntity.ok("Customer is deleted successfully..");//200
    }

    //20-a)id ile customer ı update etme -> http://localhost:8080/customers/update/1 + PUT + JSON Body
    ////Customer is updated successfully mesajı dönsün.
    ////emaili update ederken yeni değer tabloda var ve kendi maili değilse hata fırlatır. (ConflictException)
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id,@RequestBody CustomerDTO customerDTO){

        customerService.updateCustomerById(id,customerDTO);

        return ResponseEntity.ok("Customer is updated successfully...");
    }



    //21-a)tüm customerları page page gösterme ->
    // http://localhost:8080/customers/page?page=1
    //                                      &size=2
    //                                      &sort=id
    //                                      &direction=ASC  + GET

    @GetMapping("/page")
    public ResponseEntity<Page<Customer>> getAllByPage(@RequestParam("page") int page,
                                                       @RequestParam("size") int size,
                                                       @RequestParam("sort") String prop,
                                                       @RequestParam("direction")Sort.Direction direction){

        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,prop));

        Page<Customer> allCustomer=customerService.getAllCustomerByPage(pageable);

        return ResponseEntity.ok(allCustomer);
    }

    //map struct
    //22-Name ile customer getirme -> http://localhost:8080/customers/query?name=Jack + GET
    @GetMapping("/query")
    public ResponseEntity<List<Customer>> getCustomerByName(@RequestParam("name") String name){

        List<Customer> customers=customerService.getCustomerByName(name);

        return ResponseEntity.ok(customers);
    }


    //23-fullname ile customer getirme-> http://localhost:8080/customers/fullquery?
    //name=Jack&lastName=Sparrow

    @GetMapping("/fullquery")
    public ResponseEntity<List<Customer>> getAllCustomerByFullName(@RequestParam("name") String name,
                                                                   @RequestParam("lastName") String lastName){
        List<Customer> customers=customerService.getAllCustomerByFullName(name,lastName);
        return ResponseEntity.ok(customers);
    }



    //24-a-İsmi ... içeren customerlar -> http://localhost:8080/customers/jpql?name=Ja (JPQL)
    @GetMapping("/jpql")
    public ResponseEntity<List<Customer>> getCustomerByNameLike(@RequestParam("name") String word){

        List<Customer> customers=customerService.getCustomerByNameLike(word);

        return ResponseEntity.ok(customers);
    }


    //25-Idsi verilen müşterinin tüm siparişlerini getirme -> http://localhost:8080/customers/allorder/1
    @GetMapping("/allorder/{id}")
    public ResponseEntity<Set<OrderItem>> getAllOrdersByCustomerId(@PathVariable Long id){

        Customer customer=customerService.getCustomerById(id);
        Set<OrderItem> allOrders =customer.getOrders();

        return ResponseEntity.ok(allOrders);
    }



}