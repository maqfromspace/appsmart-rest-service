package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.exceptions.ProductNotFoundException;
import com.maqfromspace.appsmartrestservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<EntityModel<Product>> getProduct(@PathVariable UUID productId) {
        Product product = productRepository.findByIdAndDeleteFlagIsFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return ResponseEntity.ok(EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel(),
                linkTo(methodOn(CustomersController.class).getCustomer(product.getCustomerId())).withRel("customer"),
                linkTo(methodOn(CustomersController.class).getProducts(product.getCustomerId())).withRel("allCustomerProducts"),
                linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers")));
    }

    //Edit product
    @PutMapping("{productId}")
    public ResponseEntity<EntityModel<Product>> editProduct(@PathVariable UUID productId, @RequestBody Product product) {
        Product editedProduct = productRepository.findByIdAndDeleteFlagIsFalse(productId)
                .map(x -> {
                    x.setTitle(product.getTitle());
                    x.setPrice(product.getPrice());
                    x.setDescription(product.getDescription());
                    x.setModifiedAt(LocalDateTime.now());
                    return productRepository.save(x);
                })
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return ResponseEntity.ok(EntityModel.of(editedProduct,
                linkTo(methodOn(ProductController.class).getProduct(editedProduct.getId())).withSelfRel(),
                linkTo(methodOn(CustomersController.class).getCustomer(editedProduct.getCustomerId())).withRel("customer"),
                linkTo(methodOn(CustomersController.class).getProducts(editedProduct.getCustomerId())).withRel("allCustomerProducts"),
                linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers")));
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
        return ResponseEntity.ok(EntityModel.of(deletedProduct,
                linkTo(methodOn(ProductController.class).getProduct(deletedProduct.getId())).withSelfRel(),
                linkTo(methodOn(CustomersController.class).getCustomer(deletedProduct.getCustomerId())).withRel("customer"),
                linkTo(methodOn(CustomersController.class).getProducts(deletedProduct.getCustomerId())).withRel("allCustomerProducts"),
                linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers")));
    }
}
