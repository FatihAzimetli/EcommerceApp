package com.tpe.controller;

import com.tpe.domain.OrderItem;
import com.tpe.dto.OrderDTO;
import com.tpe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//     10-OrderController Class
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 27-a-sipariş oluşturma ->http://localhost:8080/orders/save/filter?cid=1&prod=1&quant=3
    //farklı üründe yeni sipariş, aynı üründe sayı artırılır
    @PostMapping("/save/filter")
    public ResponseEntity<String> createOrder(@RequestParam("cid") Long cid,
                                              @RequestParam("prod") Long prod,
                                              @RequestParam("quant") Integer quantity){

        orderService.createOrder(cid,prod,quantity);

        return new ResponseEntity<>("Order is created successfully...", HttpStatus.CREATED);
    }



    //28-a-tüm siparişleri getirme ->http://localhost:8080/orders
    @GetMapping()
    public ResponseEntity<List<OrderItem>> getAllOrders(){
        List<OrderItem> orderList=orderService.getAllOrders();
        return ResponseEntity.ok(orderList);//200:OK
    }


    //29-a-Id ile sipariş getirme ->http://localhost:8080/orders/5
    //29-b-DTO
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") Long id){
        OrderDTO orderDTO=orderService.getOrderDTOById(id);
        return ResponseEntity.ok(orderDTO);
    }





    //31-a-Id ile sipariş delete etme ->http://localhost:8080/orders/delete?id=5
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrder(@RequestParam("id") Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.ok("Order is deleted successfully");//200
    }




    //30-a-Id ile sipariş miktarını update etme ->http://localhost:8080/orders/update/5/quantity/30 + PUT
    //quantity=0 ise siparişi sil, aksi halde miktarı güncelle
    @PutMapping("/update/{id}/quantity/{quant}")
    public ResponseEntity<String> updateOrderQuantity(@PathVariable("id") Long id,@PathVariable("quant") Integer quantity){

        orderService.updateQuantity(id,quantity);

        return ResponseEntity.ok("Quantity is updated successfully...");
    }





}