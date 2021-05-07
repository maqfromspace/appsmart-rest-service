package com.maqfromspace.appsmartrestservice.app.dto;

import com.maqfromspace.appsmartrestservice.dto.EditCustomerRequestDto;
import com.maqfromspace.appsmartrestservice.entities.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestEditCustomerRequestDto {

    String title = "Test title for customer";

    @Test
    void editCustomerRequestDto_testConverting() {
        EditCustomerRequestDto editCustomerRequestDto = new EditCustomerRequestDto();
        editCustomerRequestDto.setTitle(title);
        Customer customer = editCustomerRequestDto.toCustomer();
        Assertions.assertEquals(customer.getTitle(), editCustomerRequestDto.getTitle());
    }
}
