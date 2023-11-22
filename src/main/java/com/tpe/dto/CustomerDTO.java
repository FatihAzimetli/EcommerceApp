package com.tpe.dto;



import com.tpe.domain.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Lastname is required!")
    private String lastName;

    @NotBlank(message = "Email is required!")
    @Email
    private String email;

    private String phone;

    public CustomerDTO(Customer customer){
        this.name= customer.getName();
        this.lastName= customer.getLastName();
        this.email= customer.getEmail();
        this.phone= customer.getPhone();
    }

}
