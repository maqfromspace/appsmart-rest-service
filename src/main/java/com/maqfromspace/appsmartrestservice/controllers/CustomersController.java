package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.dto.customer.EditCustomerRequestDto;
import com.maqfromspace.appsmartrestservice.dto.customer.NewCustomerRequestDto;
import com.maqfromspace.appsmartrestservice.entities.Customer;
import com.maqfromspace.appsmartrestservice.services.customer.CustomerService;
import com.maqfromspace.appsmartrestservice.utils.CustomerAssembler;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
@Api(tags = {"Customers"})
public class CustomersController {

    private final CustomerAssembler customerAssembler;
    private final PagedResourcesAssembler<Customer> customerPagedResourcesAssembler;
    private final CustomerService customerService;

    //Get list of all customers that have not been deleted
    @ApiOperation(value = "Get customers",
            notes = "Method allows get customers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<Customer>> getCustomers(Pageable pageable) {
        Page<Customer> customers = customerService.getCustomers(pageable);
        return customerPagedResourcesAssembler.toModel(customers, customerAssembler);
    }

    //Get customer by id
    @ApiOperation(value = "Get customer by id",
            notes = "Method allows get customer by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 404, message = "Could not found customer with id 5e0062b5-9e54-4bdf-9b61-ee695b3beb4d")
    })
    @GetMapping("{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Customer> getCustomer(@PathVariable UUID customerId) {
        Customer customer = customerService.getCustomer(customerId);
        return customerAssembler.toModel(customer);

    }

    //Create new customer
    @ApiOperation(value = "Create customer",
            notes = "Method allows create customer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Field title can't be null"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Customer> createCustomer(@Valid @RequestBody NewCustomerRequestDto newCustomerRequestDto) {
        Customer savedCustomer = customerService.addCustomer(newCustomerRequestDto.toCustomer());
        return customerAssembler.toModel(savedCustomer);
    }


    //Edit customer
    @ApiOperation(value = "Edit customer (SECURED)",
            notes = "Method allows edit customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 403, message = "JWT token isn't valid or expired"),
            @ApiResponse(code = 404, message = "Could not found customer with id 5e0062b5-9e54-4bdf-9b61-ee695b3beb4d")
    })
    @ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer_eyJhbGci1OiJIUzI1NiJ9.eyJzdWIiOiJNYWtzaW0iLCJyb2xlcyI6WyJBRE1JTl9ST0xFIl0sImlhdCI6MTYyMDIzODIxMywiZXhwIjoxNjIwMjM4MjczfQ.RNWOoxFA1NsdnvBka_obrKRODYjk-eCZ_jHQboPvokk")
    @PutMapping("{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EntityModel<Customer>> editCustomer(@NotNull @PathVariable UUID customerId, @Valid @RequestBody EditCustomerRequestDto editCustomerRequestDto) {
        Customer editedCustomer = customerService.editCustomer(customerId, editCustomerRequestDto.toCustomer());
        return ResponseEntity.ok(customerAssembler.toModel(editedCustomer));

    }

    //Delete customer
    @ApiOperation(value = "Delete customer (SECURED)",
            notes = "Method allows delete customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 403, message = "JWT token isn't valid or expired"),
            @ApiResponse(code = 404, message = "Could not found customer with id 5e0062b5-9e54-4bdf-9b61-ee695b3beb4d")
    })
    @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer_eyJhbGci1OiJIUzI1NiJ9.eyJzdWIiOiJNYWtzaW0iLCJyb2xlcyI6WyJBRE1JTl9ST0xFIl0sImlhdCI6MTYyMDIzODIxMywiZXhwIjoxNjIwMjM4MjczfQ.RNWOoxFA1NsdnvBka_obrKRODYjk-eCZ_jHQboPvokk")
    @DeleteMapping("{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Customer> deleteCustomer(@NotNull @PathVariable UUID customerId) {
        Customer deletedCustomer = customerService.deleteCustomer(customerId);
        return customerAssembler.toModel(deletedCustomer);
    }
}