package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.exeption.ConflictException;
import com.tpe.exeption.ResourceNotFoundException;
import com.tpe.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


//     5-CustomerService Class

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    //13-b-customer kaydetme
    public void saveCustomer(Customer customer) {

        boolean isExists=customerRepository.existsByEmail(customer.getEmail());
        if (isExists){
            //13-d-custom exception fırlat
            throw new ConflictException("Customer already exists by email:"+customer.getEmail());
        }

        customerRepository.save(customer);
    }

    //TODO:edit by DTO
    //14-b
    public List<CustomerDTO> getAll() {

        List<Customer> customerList=customerRepository.findAll();//"FROM Customer"
        List<CustomerDTO> customerDTOList=new ArrayList<>();

        for (Customer customer:customerList){
            CustomerDTO customerDTO=new CustomerDTO(customer);
            customerDTOList.add(customerDTO);
        }

        return customerDTOList;
    }

    //15-d-Id ile customer getirme
    public Customer getCustomerById(Long id){
        Customer customer=customerRepository.findById(id).//SELECT * FROM Customer WHERE..
                orElseThrow(()->new ResourceNotFoundException("Customer is not found by id: "+id));
        return customer;
    }


    //15-c-id ile Customer DTO getirme
    public CustomerDTO getCustomerDTO(Long id) {
        Customer foundCustomer=getCustomerById(id);

        CustomerDTO customerDTO=new CustomerDTO(foundCustomer);  //customer-->customerDTO

        return customerDTO;
    }


    //16-b
    public void deleteCustomerById(Long id) {
        getCustomerById(id);//customer yoksa custom exception fırlatılır.
        customerRepository.deleteById(id);//DELETE FROM ...WHERE
    }

    //20-b
    public void updateCustomerById(Long id, CustomerDTO customerDTO) {//aaa@mail.com

        Customer foundCustomer=getCustomerById(id);

        boolean isExist=customerRepository.existsByEmail(customerDTO.getEmail());

        if (isExist && !customerDTO.getEmail().equals(foundCustomer.getEmail())){
            throw new ConflictException("Customer already exists by email:"+customerDTO.getEmail());
        }

        foundCustomer.setName(customerDTO.getName());
        foundCustomer.setLastName(customerDTO.getLastName());
        foundCustomer.setPhone(customerDTO.getPhone());
        foundCustomer.setEmail(customerDTO.getEmail());//tabloda bir customer email:aaa@mail.com

        customerRepository.save(foundCustomer);

    }

    //21-b
    public Page<Customer> getAllCustomerByPage(Pageable pageable){
        return customerRepository.findAll(pageable);
    }


    //22-b
    public List<Customer> getCustomerByName(String name) {

        return customerRepository.findByName(name);//SELECT * FROM Customer WHERE name=''
    }


    //23-b:SELECT * FROM Customer WHERE name:.. AND lastName:...
    public List<Customer> getAllCustomerByFullName(String name, String lastName) {
        return customerRepository.findByNameAndLastName(name,lastName);
    }


    //24-b
    public List<Customer> getCustomerByNameLike(String word) {

        return customerRepository.findByNameLikeWord(word);
        //alternatif
        //  return customerRepository.findByNameContaining(word);

    }


}