package com.maqfromspace.appsmartrestservice.dto;

import com.maqfromspace.appsmartrestservice.entities.Product;
import lombok.Data;

import javax.validation.constraints.Size;

//Edit product request body
@Data
public class EditProductRequestDto {
    @Size(min = 1, max = 255, message = "The size of the 'title' field must be between 1 and 255")
    String title;
    Double price;
    @Size(max = 1024, message = "The size of the 'description' field must be between 0 and 255")
    String description;

    public Product toProduct() {
        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        return product;
    }

}
