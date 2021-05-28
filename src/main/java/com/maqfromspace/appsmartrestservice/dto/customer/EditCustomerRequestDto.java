package com.maqfromspace.appsmartrestservice.dto.customer;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

//Edit customer request body
@Data
@ApiModel(value = "EditCustomerRequestDto", description = "Edit customer request body")
public class EditCustomerRequestDto {
    @ApiModelProperty(notes = "title", example = "Maksim")
    @Size(min = 1, max = 255, message = "The size of the 'title' field must be between 1 and 255")
    String title;
    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setTitle(title);
        return customer;
    }
}

