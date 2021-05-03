package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.exceptions.ProductNotFoundException;
import com.maqfromspace.appsmartrestservice.repositories.ProductRepository;
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

    private final ProductRepository productRepository;
    private final ProductAssembler productAssembler;
    public ProductController(@Autowired ProductRepository productRepository, @Autowired ProductAssembler productAssembler) {
        this.productRepository = productRepository;
        this.productAssembler = productAssembler;
    }

    //Return product by id
    @GetMapping("{productId}")
    public ResponseEntity<EntityModel<Product>> getProduct(@PathVariable UUID productId) {
        Product product = productRepository.findByIdAndDeleteFlagIsFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return ResponseEntity.ok(productAssembler.toModel(product));
    }

    //Edit product
    @PutMapping("{productId}")
    public ResponseEntity<EntityModel<Product>> editProduct(@PathVariable UUID productId, @RequestBody Product product) {
        Product editedProduct = productRepository.findByIdAndDeleteFlagIsFalse(productId)
                .map(x -> {
                    x.setTitle(product.getTitle());
                    x.setPrice(product.getPrice());
                    x.setDescription(product.getDescription());
                    return productRepository.save(x);
                })
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return ResponseEntity.ok(productAssembler.toModel(editedProduct));
    }

    //Delete product
    @DeleteMapping("{productId}")
    public ResponseEntity<EntityModel<Product>> deleteProduct(@PathVariable UUID productId) {
        Product deletedProduct = productRepository.findByIdAndDeleteFlagIsFalse(productId)
                .map(x -> {
                    x.setDeleteFlag(true);
                    return productRepository.save(x);
                })
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return ResponseEntity.ok(productAssembler.toModel(deletedProduct));
    }
}
