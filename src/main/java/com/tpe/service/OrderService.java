package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.domain.OrderItem;
import com.tpe.domain.Product;
import com.tpe.dto.OrderDTO;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.exeption.ResourceNotFoundException;
import com.tpe.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.PreUpdate;
import java.util.List;

//     11-OrderService Class
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CustomerService customerService;

    private final ProductService productService;

    //27-b
    public void createOrder(Long cid, Long prod, Integer quantity) {

        Customer customer=customerService.getCustomerById(cid);//1:Jack
        Product product=productService.getProductById(prod);//1:laptop123
        OrderItem order;

        //aynı müşteri aynı ürün için sipariş veriyorsa sadece miktarı artıralım
        //farklı ise yeni sipariş oluştaralım....

        //daha önce aynı ürün için aynı müşteri sipariş vermiş mi
        boolean isExistsSameProduct=orderRepository.existsByCustomerAndProduct(customer,product);
        if (isExistsSameProduct){
            order=orderRepository.findByCustomerIdAndProductId(cid,prod);
            //order= orderRepository.findByCustomerAndProduct(customer,product);
            order.setQuantity(order.getQuantity()+quantity);
        }else {
            order=new OrderItem();
            order.setCustomer(customer);
            order.setProduct(product);
            order.setQuantity(quantity);
        }
        //order.countTotalPrice();-->PrePersist,PreUpdate old için bu satıra gerek yok
        orderRepository.save(order);
    }


    // 28-b
    public List<OrderItem> getAllOrders() {
        return orderRepository.findAll();
    }

    //29-c
    public OrderItem getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found by id: "+id));
    }

    //29-d
    public OrderDTO getOrderDTOById(Long id) {
        OrderItem order=getOrderById(id);
        OrderDTO orderDTO=new OrderDTO(order.getQuantity(), order.getTotalPrice(),order.getProduct());
        return orderDTO;
    }




    //31-b
    public void deleteOrderById(Long id) {
        OrderItem order=getOrderById(id);
        orderRepository.delete(order);
    }

    //30-b
    public void updateQuantity(Long id, Integer quantity) {

        if (quantity==0){
            deleteOrderById(id);
        }else {
            OrderItem order=getOrderById(id);
            order.setQuantity(quantity);
            //PreUpdate-->order.countTotalPrice();
            orderRepository.save(order);//update
        }
    }

}