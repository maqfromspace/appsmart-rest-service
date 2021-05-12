package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.dto.EditProductRequestDto;
import com.maqfromspace.appsmartrestservice.dto.NewProductRequestDto;
import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.services.product.ProductService;
import com.maqfromspace.appsmartrestservice.utils.ProductAssembler;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RestController
@RequestMapping("api/v1")
@Api(tags = {"Products"})
public class ProductController {

    private final ProductService productService;
    private final ProductAssembler productAssembler;
    private final PagedResourcesAssembler<Product> productPagedResourcesAssembler;

    //Return product by id
    @ApiOperation(value = "Get product by id",
            notes = "Method allows get product by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
    })
    @GetMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Product> getProduct(@NotNull @PathVariable UUID productId) {
        Product product = productService.getProduct(productId);
        return productAssembler.toModel(product);
    }

    //Edit product
    @ApiOperation(value = "Edit product (SECURED)",
            notes = "Method allows edit product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 403, message = "JWT token isn't valid or expired"),
            @ApiResponse(code = 404, message = "Could not found product with id 5e0062b5-9e54-4bdf-9b61-ee695b3beb4d")
    })
    @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer_eyJhbGci1OiJIUzI1NiJ9.eyJzdWIiOiJNYWtzaW0iLCJyb2xlcyI6WyJBRE1JTl9ST0xFIl0sImlhdCI6MTYyMDIzODIxMywiZXhwIjoxNjIwMjM4MjczfQ.RNWOoxFA1NsdnvBka_obrKRODYjk-eCZ_jHQboPvokk")
    @PutMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Product> editProduct(@NotNull @PathVariable UUID productId, @RequestBody EditProductRequestDto editProductRequestDto) {
        Product editedProduct = productService.editProduct(productId, editProductRequestDto.toProduct());
        return productAssembler.toModel(editedProduct);
    }
    //Delete product
    @ApiOperation(value = "Delete product (SECURED)",
            notes = "Method allows delete product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 403, message = "JWT token isn't valid or expired"),
            @ApiResponse(code = 404, message = "Could not found product with id 5e0062b5-9e54-4bdf-9b61-ee695b3beb4d")
    })
    @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer_eyJhbGci1OiJIUzI1NiJ9.eyJzdWIiOiJNYWtzaW0iLCJyb2xlcyI6WyJBRE1JTl9ST0xFIl0sImlhdCI6MTYyMDIzODIxMywiZXhwIjoxNjIwMjM4MjczfQ.RNWOoxFA1NsdnvBka_obrKRODYjk-eCZ_jHQboPvokk")
    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Product> deleteProduct(@NotNull @PathVariable UUID productId) {
        Product deletedProduct = productService.deleteProduct(productId);
        return productAssembler.toModel(deletedProduct);
    }

    //Create new product for customer
    @ApiOperation(value = "Create new product for customer",
            notes = "Method allows create new product for customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 404, message = "Could not found customer with id 5e0062b5-9e54-4bdf-9b61-ee695b3beb4d")
    })
    @PostMapping("customers/{customerId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Product> createProduct(@NotNull @PathVariable UUID customerId, @Valid @RequestBody NewProductRequestDto newProductRequestDto) {
        Product savedProduct = productService.createProduct(customerId, newProductRequestDto.toProduct());
        return productAssembler.toModel(savedProduct);
    }

    @ApiOperation(value = "Get customer's product",
            notes = "Method allows get customer's product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    //Get customer's products
    @GetMapping("customers/{customerId}/products")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<Product>> getProducts(@NotNull @PathVariable UUID customerId, Pageable pageable) {
        Page<Product> products = productService.getCustomersProduct(customerId, pageable);
        return productPagedResourcesAssembler.toModel(products, productAssembler);
    }
}
