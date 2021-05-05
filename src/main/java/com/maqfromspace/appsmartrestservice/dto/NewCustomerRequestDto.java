package com.maqfromspace.appsmartrestservice.dto;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//New customer request body
@Data
public class NewCustomerRequestDto {
    @NotNull(message = "Field title can't be null")
    @Size(min = 1, max = 255, message = "The size of the 'title' field must be between 1 and 255")
    String title;

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setTitle(title);
        return customer;
    }
}
