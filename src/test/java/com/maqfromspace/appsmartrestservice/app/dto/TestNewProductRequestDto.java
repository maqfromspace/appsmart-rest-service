package com.maqfromspace.appsmartrestservice.app.dto;

import com.maqfromspace.appsmartrestservice.dto.NewCustomerRequestDto;
import com.maqfromspace.appsmartrestservice.dto.NewProductRequestDto;
import com.maqfromspace.appsmartrestservice.entities.Customer;
import com.maqfromspace.appsmartrestservice.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestNewProductRequestDto {

    String title = "Test title for product";
    Double price = 10.5;
    String description = "Test description for product";

    @Test
    void newProductRequestDto_testConverting() {
        NewProductRequestDto newProductRequestDto = new NewProductRequestDto();
        newProductRequestDto.setTitle(title);
        newProductRequestDto.setPrice(price);
        newProductRequestDto.setDescription(description);

        Product product = newProductRequestDto.toProduct();

        Assertions.assertEquals(product.getTitle(), newProductRequestDto.getTitle());
        Assertions.assertEquals(product.getPrice(), newProductRequestDto.getPrice());
        Assertions.assertEquals(product.getDescription(), newProductRequestDto.getDescription());
    }
}
