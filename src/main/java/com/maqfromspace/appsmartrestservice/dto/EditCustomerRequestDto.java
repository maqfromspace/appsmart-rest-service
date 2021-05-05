package com.maqfromspace.appsmartrestservice.dto;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import lombok.Data;

import javax.validation.constraints.Size;

//Edit customer request body
@Data
public class EditCustomerRequestDto {
    @Size(min = 1, max = 255, message = "The size of the 'title' field must be between 1 and 255")
    String title;
    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setTitle(title);
        return customer;
    }
}
