package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.exceptions.ProductNotFoundException;
import com.maqfromspace.appsmartrestservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

//Product controller
@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Return product by id
    @GetMapping("{productId}")
    public Product getProduct(@PathVariable UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    //Edit product
    @PutMapping("{productId}")
    public Product editProduct(@PathVariable UUID productId, @RequestBody Product product) {
        return productRepository.findById(productId)
                .map(x -> {
                    x.setTitle(product.getTitle());
                    x.setPrice(product.getPrice());
                    x.setDescription(product.getDescription());
                    x.setModifiedAt(LocalDateTime.now());
                    return productRepository.save(x);
                })
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    //Delete product
    @DeleteMapping("{productId}")
    public void deleteCustomer(@PathVariable UUID productId) {
        productRepository.findById(productId)
                .ifPresent(product -> {
                    product.setDeleted(true);
                    product.setModifiedAt(LocalDateTime.now());
                    productRepository.save(product);
                });
    }
}
