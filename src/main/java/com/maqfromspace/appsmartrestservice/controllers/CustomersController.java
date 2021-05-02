package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.exceptions.CustomerNotFoundException;
import com.maqfromspace.appsmartrestservice.repositories.CustomersRepository;
import com.maqfromspace.appsmartrestservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    //Return list of all customers
    @GetMapping
    public CollectionModel<EntityModel<Customer>> getCustomers() {
        List<EntityModel<Customer>> customersModel = customersRepository.findAll()
                .stream()
                .map(customer -> EntityModel.of(customer,
                        linkTo(methodOn(CustomersController.class).getCustomer(customer.getId())).withSelfRel(),
                        linkTo(methodOn(CustomersController.class).getProducts(customer.getId())).withRel("products"),
                        linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers"))).collect(Collectors.toList());
        return CollectionModel.of(customersModel, linkTo(methodOn(CustomersController.class).getCustomers()).withSelfRel());

    }

    //Return customer by id
    @GetMapping("{customerId}")
    public EntityModel<Customer> getCustomer(@PathVariable UUID customerId) {
        Customer customer = customersRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        return EntityModel.of(customer,
                linkTo(methodOn(CustomersController.class).getCustomer(customerId)).withSelfRel(),
                linkTo(methodOn(CustomersController.class).getProducts(customer.getId())).withRel("products"),
                linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers"));

    }

    //Create new customer
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customersRepository.save(customer);
    }

    //Edit customer
    @PutMapping("{customerId}")
    public Customer editCustomer(@PathVariable UUID customerId, @RequestBody Customer customer) {
        return customersRepository.findById(customerId)
                .map(x -> {
                    x.setTitle(customer.getTitle());
                    x.setModifiedAt(LocalDateTime.now());
                    return customersRepository.save(x);
                })
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    //Delete customer
    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable UUID customerId) {
        customersRepository.findById(customerId)
                .ifPresent(customer -> {
                    customer.setDeleted(true);
                    customer.setModifiedAt(LocalDateTime.now());
                    customersRepository.save(customer);
                });
    }

    //Create new product for customer
    @PostMapping("{customerId}/products")
    public Product createCustomer(@PathVariable UUID customerId, @RequestBody Product product) {
        product.setCustomerId(customerId);
        return productRepository.save(product);
    }

    @GetMapping("{customerId}/products")
    public List<Product> getProducts(@PathVariable UUID customerId) {
        return productRepository.findAllByCustomerId(customerId);
    }
}
