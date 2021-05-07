package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.dto.EditProductRequestDto;
import com.maqfromspace.appsmartrestservice.dto.NewProductRequestDto;
import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.services.product.ProductService;
import com.maqfromspace.appsmartrestservice.utils.ProductAssembler;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

//Product controller
@RestController
@RequestMapping("api/v1")
@Api(tags = {"Products"})
public class ProductController {

    private final ProductService productService;
    private final ProductAssembler productAssembler;
    private final PagedResourcesAssembler<Product> productPagedResourcesAssembler;


    public ProductController(@Autowired ProductService productService,
                             @Autowired ProductAssembler productAssembler,
                             @Autowired PagedResourcesAssembler<Product> productPagedResourcesAssembler) {
        this.productService = productService;
        this.productAssembler = productAssembler;
        this.productPagedResourcesAssembler = productPagedResourcesAssembler;
    }

    //Return product by id
    @GetMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Product> getProduct(@NotNull @PathVariable UUID productId) {
        Product product = productService.getProduct(productId);
        return productAssembler.toModel(product);
    }

    //Edit product
    @PutMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Product> editProduct(@NotNull @PathVariable UUID productId, @RequestBody EditProductRequestDto editProductRequestDto) {
        Product editedProduct = productService.editProduct(productId, editProductRequestDto.toProduct());
        return productAssembler.toModel(editedProduct);
    }

    //Delete product
    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Product> deleteProduct(@NotNull @PathVariable UUID productId) {
        Product deletedProduct = productService.deleteProduct(productId);
        return productAssembler.toModel(deletedProduct);
    }

    //Create new product for customer
    @PostMapping("customers/{customerId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Product> createProduct(@NotNull @PathVariable UUID customerId, @Valid @RequestBody NewProductRequestDto newProductRequestDto) {
        Product savedProduct = productService.createProduct(customerId, newProductRequestDto.toProduct());
        return productAssembler.toModel(savedProduct);
    }

    //Get customer's products
    @GetMapping("customers/{customerId}/products")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<Product>> getProducts(@NotNull @PathVariable UUID customerId, Pageable pageable) {
        Page<Product> products = productService.getCustomersProduct(customerId, pageable);
        return productPagedResourcesAssembler.toModel(products, productAssembler);
    }
}
