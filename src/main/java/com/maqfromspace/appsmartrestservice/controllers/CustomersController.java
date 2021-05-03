package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.exceptions.CustomerNotFoundException;
import com.maqfromspace.appsmartrestservice.repositories.CustomersRepository;
import com.maqfromspace.appsmartrestservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//Customers controller
@RestController
@RequestMapping("customers")
public class CustomersController {

    private final CustomersRepository customersRepository;
    private final ProductRepository productRepository;

    public CustomersController(@Autowired CustomersRepository customersRepository, @Autowired ProductRepository productRepository) {
        this.customersRepository = customersRepository;
        this.productRepository = productRepository;
    }

    //Get list of all customers that have not been deleted
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Customer>>> getCustomers() {
        List<EntityModel<Customer>> customersModel = customersRepository.findAllByDeleteFlagIsFalse()
                .stream()
                .map(customer -> EntityModel.of(customer,
                        linkTo(methodOn(CustomersController.class).getCustomer(customer.getId())).withSelfRel(),
                        linkTo(methodOn(CustomersController.class).getProducts(customer.getId())).withRel("products"),
                        linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                CollectionModel.of(customersModel,
                        linkTo(methodOn(CustomersController.class).getCustomers()).withSelfRel()
                )
        );
    }

    //Get customer by id
    @GetMapping("{customerId}")
    public ResponseEntity<EntityModel<Customer>> getCustomer(@PathVariable UUID customerId) {
        Customer customer = customersRepository.findByIdAndDeleteFlagIsFalse(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        return ResponseEntity.ok(EntityModel.of(customer,
                linkTo(methodOn(CustomersController.class).getCustomer(customerId)).withSelfRel(),
                linkTo(methodOn(CustomersController.class).getProducts(customer.getId())).withRel("products"),
                linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers")));

    }

    //Create new customer
    @PostMapping
    public ResponseEntity<EntityModel<Customer>> createCustomer(@RequestBody Customer customer) {
        Customer savedCostumer = customersRepository.save(customer);
        URI location = linkTo(methodOn(CustomersController.class).getCustomer(savedCostumer.getId())).withSelfRel().toUri();
        return ResponseEntity.created(location)
                .body(EntityModel.of(savedCostumer,
                        linkTo(methodOn(CustomersController.class).getCustomer(savedCostumer.getId())).withSelfRel(),
                        linkTo(methodOn(CustomersController.class).getProducts(savedCostumer.getId())).withRel("products"),
                        linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers")));
    }

    //Edit customer
    @PutMapping("{customerId}")
    public ResponseEntity<EntityModel<Customer>> editCustomer(@PathVariable UUID customerId, @RequestBody Customer customer) {
        Customer editedCustomer = customersRepository.findByIdAndDeleteFlagIsFalse(customerId)
                .map(x -> {
                    x.setTitle(customer.getTitle());
                    x.setModifiedAt(LocalDateTime.now());
                    return customersRepository.save(x);
                })
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return ResponseEntity.ok(EntityModel.of(editedCustomer,
                        linkTo(methodOn(CustomersController.class).getCustomer(editedCustomer.getId())).withSelfRel(),
                        linkTo(methodOn(CustomersController.class).getProducts(editedCustomer.getId())).withRel("products"),
                        linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers")));

    }

    //Delete customer
    @DeleteMapping("{customerId}")
    public ResponseEntity<EntityModel<Customer>> deleteCustomer(@PathVariable UUID customerId) {
        Customer deletedCustomer = customersRepository.findByIdAndDeleteFlagIsFalse(customerId)
                .map(customer -> {
                    customer.setDeleteFlag(true);
                    return customersRepository.save(customer);
                })
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return ResponseEntity.ok(EntityModel.of(deletedCustomer,
                linkTo(methodOn(CustomersController.class).getCustomer(deletedCustomer.getId())).withSelfRel(),
                linkTo(methodOn(CustomersController.class).getProducts(deletedCustomer.getId())).withRel("products"),
                linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers")));
    }

    //Create new product for customer
    @PostMapping("{customerId}/products")
    public ResponseEntity<EntityModel<Product>> createProduct(@PathVariable UUID customerId, @RequestBody Product product) {
        product.setCustomerId(customerId);
        Product savedProduct = productRepository.save(product);
        URI location = linkTo(methodOn(CustomersController.class).getCustomer(savedProduct.getId())).withSelfRel().toUri();
        return ResponseEntity.created(location)
                .body(EntityModel.of(savedProduct,
                        linkTo(methodOn(ProductController.class).getProduct(savedProduct.getId())).withSelfRel(),
                        linkTo(methodOn(CustomersController.class).getCustomer(savedProduct.getCustomerId())).withRel("customer"),
                        linkTo(methodOn(CustomersController.class).getProducts(savedProduct.getCustomerId())).withRel("allCustomerProducts"),
                        linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers")));
    }

    //Get customer's products
    @GetMapping("{customerId}/products")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getProducts(@PathVariable UUID customerId) {
        List<EntityModel<Product>> customersModel = productRepository.findByCustomerIdAndDeleteFlagIsFalse(customerId)
                .stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel(),
                        linkTo(methodOn(CustomersController.class).getCustomer(product.getCustomerId())).withRel("customer"),
                        linkTo(methodOn(CustomersController.class).getProducts(product.getCustomerId())).withRel("allCustomerProducts"),
                        linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                CollectionModel.of(customersModel,
                    linkTo(methodOn(CustomersController.class).getProducts(customerId)).withSelfRel()
                ));
    }
}
