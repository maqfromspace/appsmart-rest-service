package com.maqfromspace.appsmartrestservice.dto.product;

import com.maqfromspace.appsmartrestservice.entities.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

//Edit product request body
@ApiModel(value = "EditProductRequestDto", description = "Edit product request body")
@Data
public class EditProductRequestDto {
    @ApiModelProperty(notes = "title", example = "Mars")
    @Size(min = 1, max = 255, message = "The size of the 'title' field must be between 1 and 255")
    String title;
    @ApiModelProperty(notes = "price", example = "10.50")
    Double price;
    @ApiModelProperty(notes = "description", example = "Chocolate with nuts!")
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
