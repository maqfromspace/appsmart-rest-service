package com.maqfromspace.appsmartrestservice.dto;

import com.maqfromspace.appsmartrestservice.entities.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// New product request body
@ApiModel(value = "NewProductRequestDto", description = "New product request body")
@Data
public class NewProductRequestDto {

    @ApiModelProperty(notes = "title", example = "Mars", required = true)
    @NotNull(message = "Field 'title' can't be null")
    @Size(min = 1, max = 255, message = "The size of the 'title' field must be between 1 and 255")
    String title;

    @ApiModelProperty(notes = "description", example = "Chocolate with nuts!")
    @Size(max = 1024, message = "The size of the 'description' field must be between 0 and 255")
    String description;

    @ApiModelProperty(notes = "price", example = "10.50", required = true)
    @NotNull(message = "Field 'price' can't be null")
    Double price;



    public Product toProduct() {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }
}
