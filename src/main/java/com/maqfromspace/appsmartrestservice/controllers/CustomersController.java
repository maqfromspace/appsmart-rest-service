package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.services.customer.CustomerService;
import com.maqfromspace.appsmartrestservice.services.product.ProductService;
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
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//Customers controller
@RestController
@RequestMapping("customers")
public class CustomersController {

    private final CustomerAssembler customerAssembler;
    private final ProductAssembler productAssembler;
    private final PagedResourcesAssembler<Customer> customerPagedResourcesAssembler;
    private final PagedResourcesAssembler<Product> productPagedResourcesAssembler;
    private final CustomerService customerService;
    private final ProductService productService;

    public CustomersController(@Autowired CustomerAssembler customerAssembler,
                               @Autowired ProductAssembler productAssembler,
                               @Autowired PagedResourcesAssembler<Customer> customerPagedResourcesAssembler,
                               @Autowired PagedResourcesAssembler<Product> productPagedResourcesAssembler,
                               @Autowired CustomerService customerService,
                               @Autowired ProductService productService) {
        this.customerAssembler = customerAssembler;
        this.productAssembler = productAssembler;
        this.customerPagedResourcesAssembler = customerPagedResourcesAssembler;
        this.productPagedResourcesAssembler = productPagedResourcesAssembler;
        this.customerService = customerService;
        this.productService = productService;
    }

    //Get list of all customers that have not been deleted
    @GetMapping
    public PagedModel<EntityModel<Customer>> getCustomers(Pageable pageable) {
        Page<Customer> customers = customerService.getCustomers(pageable);
        return customerPagedResourcesAssembler.toModel(customers, customerAssembler);
    }

    //Get customer by id
    @GetMapping("{customerId}")
    public ResponseEntity<EntityModel<Customer>> getCustomer(@PathVariable UUID customerId) {
        Customer customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customerAssembler.toModel(customer));

    }

    //Create new customer
    @PostMapping
    public ResponseEntity<EntityModel<Customer>> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.addCustomer(customer);
        URI location = linkTo(methodOn(CustomersController.class)
                .getCustomer(savedCustomer.getId()))
                .withSelfRel()
                .toUri();
        return ResponseEntity.created(location)
                .body(customerAssembler.toModel(customer));
    }

    //Edit customer
    @PutMapping("{customerId}")
    public ResponseEntity<EntityModel<Customer>> editCustomer(@PathVariable UUID customerId, @RequestBody Customer customer) {
        Customer editedCustomer = customerService.editCustomer(customerId, customer);
        return ResponseEntity.ok(customerAssembler.toModel(editedCustomer));

    }

    //Delete customer
    @DeleteMapping("{customerId}")
    public ResponseEntity<EntityModel<Customer>> deleteCustomer(@PathVariable UUID customerId) {
        Customer deletedCustomer = customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(customerAssembler.toModel(deletedCustomer));
    }

    //Create new product for customer
    @PostMapping("{customerId}/products")
    public ResponseEntity<EntityModel<Product>> createProduct(@PathVariable UUID customerId, @RequestBody Product product) {
        Product savedProduct = productService.createProduct(customerId, product);
        URI location = linkTo(methodOn(CustomersController.class).getCustomer(savedProduct.getId())).withSelfRel().toUri();
        return ResponseEntity.created(location)
                .body(productAssembler.toModel(savedProduct));
    }

    //Get customer's products
    @GetMapping("{customerId}/products")
    public PagedModel<EntityModel<Product>> getProducts(@PathVariable UUID customerId, Pageable pageable) {
        Page<Product> products = productService.getCustomersProduct(customerId, pageable);
        return productPagedResourcesAssembler.toModel(products, productAssembler);
    }
}