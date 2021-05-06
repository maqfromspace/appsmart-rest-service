package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.dto.EditProductRequestDto;
import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.services.product.ProductService;
import com.maqfromspace.appsmartrestservice.utils.ProductAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

//Product controller
@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductAssembler productAssembler;

    public ProductController(@Autowired ProductService productService,
                             @Autowired ProductAssembler productAssembler) {
        this.productService = productService;
        this.productAssembler = productAssembler;
    }

    //Return product by id
    @GetMapping("{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Product> getProduct(@NotNull @PathVariable UUID productId) {
        Product product = productService.getProduct(productId);
        return productAssembler.toModel(product);
    }

    //Edit product
    @PutMapping("{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Product> editProduct(@NotNull @PathVariable UUID productId, @RequestBody EditProductRequestDto editProductRequestDto) {
        Product editedProduct = productService.editProduct(productId, editProductRequestDto.toProduct());
        return productAssembler.toModel(editedProduct);
    }

    //Delete product
    @DeleteMapping("{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Product> deleteProduct(@NotNull @PathVariable UUID productId) {
        Product deletedProduct = productService.deleteProduct(productId);
        return productAssembler.toModel(deletedProduct);
    }
}
