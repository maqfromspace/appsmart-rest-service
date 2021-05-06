package com.maqfromspace.appsmartrestservice.app.dto;

import com.maqfromspace.appsmartrestservice.dto.NewCustomerRequestDto;
import com.maqfromspace.appsmartrestservice.entities.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestNewCustomerRequestDto {

    String title = "Test title for customer";

    @Test
    void newCustomerRequestDto_testConverting() {
        NewCustomerRequestDto newCustomerRequestDto = new NewCustomerRequestDto();
        newCustomerRequestDto.setTitle(title);
        Customer customer = newCustomerRequestDto.toCustomer();
        Assertions.assertEquals(customer.getTitle(), newCustomerRequestDto.getTitle());
    }
}
