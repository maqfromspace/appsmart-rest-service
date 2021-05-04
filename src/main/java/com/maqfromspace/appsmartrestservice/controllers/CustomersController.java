package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.exceptions.CustomerNotFoundException;
import com.maqfromspace.appsmartrestservice.repositories.CustomersRepository;
import com.maqfromspace.appsmartrestservice.repositories.ProductRepository;
import com.maqfromspace.appsmartrestservice.utils.CustomerAssembler;
import com.maqfromspace.appsmartrestservice.utils.ProductAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//Customers controller
@RestController
@RequestMapping("customers")
public class CustomersController {

    private final CustomersRepository customersRepository;
    private final ProductRepository productRepository;
    private final CustomerAssembler customerAssembler;
    private final ProductAssembler productAssembler;
    private final PagedResourcesAssembler<Customer> customerPagedResourcesAssembler;
    private final PagedResourcesAssembler<Product> productPagedResourcesAssembler;

    public CustomersController(@Autowired CustomersRepository customersRepository,
                               @Autowired ProductRepository productRepository,
                               @Autowired CustomerAssembler customerAssembler,
                               @Autowired ProductAssembler productAssembler,
                               @Autowired PagedResourcesAssembler<Customer> customerPagedResourcesAssembler,
                               @Autowired PagedResourcesAssembler<Product> productPagedResourcesAssembler) {
        this.customersRepository = customersRepository;
        this.productRepository = productRepository;
        this.customerAssembler = customerAssembler;
        this.productAssembler = productAssembler;
        this.customerPagedResourcesAssembler = customerPagedResourcesAssembler;
        this.productPagedResourcesAssembler = productPagedResourcesAssembler;
    }

    //Get list of all customers that have not been deleted
    @GetMapping
    public PagedModel<EntityModel<Customer>> getCustomers(Pageable pageable) {
        Page<Customer> customer = customersRepository.findAllByDeleteFlagIsFalse(pageable);
        return customerPagedResourcesAssembler.toModel(customer, customerAssembler);
    }

    //Get customer by id
    @GetMapping("{customerId}")
    public ResponseEntity<EntityModel<Customer>> getCustomer(@PathVariable UUID customerId) {
        Customer customer = customersRepository.findByIdAndDeleteFlagIsFalse(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return ResponseEntity.ok(customerAssembler.toModel(customer));

    }

    //Create new customer
    @PostMapping
    public ResponseEntity<EntityModel<Customer>> createCustomer(@RequestBody Customer customer) {
        Customer savedCostumer = customersRepository.save(customer);
        URI location = linkTo(methodOn(CustomersController.class).getCustomer(savedCostumer.getId())).withSelfRel().toUri();
        return ResponseEntity.created(location)
                .body(customerAssembler.toModel(customer));
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
        return ResponseEntity.ok(customerAssembler.toModel(editedCustomer));

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
        return ResponseEntity.ok(customerAssembler.toModel(deletedCustomer));
    }

    //Create new product for customer
    @PostMapping("{customerId}/products")
    public ResponseEntity<EntityModel<Product>> createProduct(@PathVariable UUID customerId, @RequestBody Product product) {
        Product savedProduct = customersRepository.findByIdAndDeleteFlagIsFalse(customerId)
                .map(x -> {
                    product.setCustomer(x);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        URI location = linkTo(methodOn(CustomersController.class).getCustomer(savedProduct.getId())).withSelfRel().toUri();
        return ResponseEntity.created(location)
                .body(productAssembler.toModel(product));
    }

    //Get customer's products
    @GetMapping("{customerId}/products")
    public PagedModel<EntityModel<Product>> getProducts(@PathVariable UUID customerId, Pageable pageable) {
        Page<Product> products = productRepository.findByCustomerIdAndDeleteFlagIsFalse(customerId, pageable);
        return productPagedResourcesAssembler.toModel(products, productAssembler);
    }
}
