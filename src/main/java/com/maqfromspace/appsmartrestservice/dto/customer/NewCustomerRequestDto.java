package com.maqfromspace.appsmartrestservice.dto.customer;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//New customer request body
@ApiModel(value = "NewCustomerRequestDto", description = "Add customer request body")
@Data
public class NewCustomerRequestDto {
    @ApiModelProperty(notes = "title", example = "Maksim", required = true)
    @NotNull(message = "Field title can't be null")
    @Size(min = 1, max = 255, message = "The size of the 'title' field must be between 1 and 255")
    String title;

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setTitle(title);
        return customer;
    }
}
