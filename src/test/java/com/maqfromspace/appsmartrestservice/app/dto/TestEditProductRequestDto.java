package com.maqfromspace.appsmartrestservice.app.dto;

import com.maqfromspace.appsmartrestservice.dto.EditProductRequestDto;
import com.maqfromspace.appsmartrestservice.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestEditProductRequestDto {

    String title = "Test title for product";
    Double price = 10.5;
    String description = "Test description for product";

    @Test
    void editProductRequestDto_testConverting() {
        EditProductRequestDto editProductRequestDto = new EditProductRequestDto();
        editProductRequestDto.setTitle(title);
        editProductRequestDto.setPrice(price);
        editProductRequestDto.setDescription(description);

        Product product = editProductRequestDto.toProduct();

        Assertions.assertEquals(product.getTitle(), editProductRequestDto.getTitle());
        Assertions.assertEquals(product.getPrice(), editProductRequestDto.getPrice());
        Assertions.assertEquals(product.getDescription(), editProductRequestDto.getDescription());
    }
}
