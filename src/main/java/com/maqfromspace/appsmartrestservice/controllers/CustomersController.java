package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.dto.EditCustomerRequestDto;
import com.maqfromspace.appsmartrestservice.dto.NewCustomerRequestDto;
import com.maqfromspace.appsmartrestservice.entities.Customer;
import com.maqfromspace.appsmartrestservice.services.customer.CustomerService;
import com.maqfromspace.appsmartrestservice.utils.CustomerAssembler;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

//Customers controller
@RestController
@RequestMapping("api/v1/customers")
@Api(tags = {"Customers"})
public class CustomersController {

    private final CustomerAssembler customerAssembler;
    private final PagedResourcesAssembler<Customer> customerPagedResourcesAssembler;
    private final CustomerService customerService;

    public CustomersController(@Autowired CustomerAssembler customerAssembler,
                               @Autowired PagedResourcesAssembler<Customer> customerPagedResourcesAssembler,
                               @Autowired CustomerService customerService) {
        this.customerAssembler = customerAssembler;
        this.customerPagedResourcesAssembler = customerPagedResourcesAssembler;
        this.customerService = customerService;
    }

    //Get list of all customers that have not been deleted
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<Customer>> getCustomers(Pageable pageable) {
        Page<Customer> customers = customerService.getCustomers(pageable);
        return customerPagedResourcesAssembler.toModel(customers, customerAssembler);
    }

    //Get customer by id
    @GetMapping("{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Customer> getCustomer(@PathVariable UUID customerId) {
        Customer customer = customerService.getCustomer(customerId);
        return customerAssembler.toModel(customer);

    }

    //Create new customer
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Customer> createCustomer(@Valid @RequestBody NewCustomerRequestDto newCustomerRequestDto) {
        Customer savedCustomer = customerService.addCustomer(newCustomerRequestDto.toCustomer());
        return customerAssembler.toModel(savedCustomer);
    }

    //Edit customer
    @PutMapping("{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EntityModel<Customer>> editCustomer(@NotNull @PathVariable UUID customerId, @Valid @RequestBody EditCustomerRequestDto editCustomerRequestDto) {
        Customer editedCustomer = customerService.editCustomer(customerId, editCustomerRequestDto.toCustomer());
        return ResponseEntity.ok(customerAssembler.toModel(editedCustomer));

    }

    //Delete customer
    @DeleteMapping("{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Customer> deleteCustomer(@NotNull @PathVariable UUID customerId) {
        Customer deletedCustomer = customerService.deleteCustomer(customerId);
        return customerAssembler.toModel(deletedCustomer);
    }
}