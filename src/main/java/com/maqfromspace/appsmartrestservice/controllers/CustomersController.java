package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import com.maqfromspace.appsmartrestservice.exceptions.CustomerNotFoundException;
import com.maqfromspace.appsmartrestservice.repositories.CustomersRepository;
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

    public CustomersController(@Autowired CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    //Return list of all customers
    @GetMapping
    public List<Customer> getCustomers() {
        return customersRepository.findAll();
    }

    //Return customer by id
    @GetMapping("{customerId}")
    public Customer getCustomer(@PathVariable UUID customerId) {
        return customersRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
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
}
