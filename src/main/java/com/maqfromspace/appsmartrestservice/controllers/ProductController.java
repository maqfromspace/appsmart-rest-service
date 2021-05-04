package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.services.product.ProductService;
import com.maqfromspace.appsmartrestservice.utils.ProductAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//Product controller
@RestController
@RequestMapping("products")
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
    public ResponseEntity<EntityModel<Product>> getProduct(@PathVariable UUID productId) {
        Product product = productService.getProduct(productId);
        return ResponseEntity.ok(productAssembler.toModel(product));
    }

    //Edit product
    @PutMapping("{productId}")
    public ResponseEntity<EntityModel<Product>> editProduct(@PathVariable UUID productId, @RequestBody Product product) {
        Product editedProduct = productService.editProduct(productId, product);
        return ResponseEntity.ok(productAssembler.toModel(editedProduct));
    }

    //Delete product
    @DeleteMapping("{productId}")
    public ResponseEntity<EntityModel<Product>> deleteProduct(@PathVariable UUID productId) {
        Product deletedProduct = productService.deleteProduct(productId);
        return ResponseEntity.ok(productAssembler.toModel(deletedProduct));
    }
}
