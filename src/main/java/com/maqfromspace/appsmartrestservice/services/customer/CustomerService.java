package com.maqfromspace.appsmartrestservice.services.customer;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CustomerService {
    //Get customers page
    Page<Customer> getCustomers(Pageable pageable);
    //Get customer with id {customerId}
    Customer getCustomer(UUID customerId);
    //Create new customer
    Customer addCustomer(Customer customer);
    //Edit customer with id {customerId}
    Customer editCustomer(UUID customerId, Customer customer);
    //Delete customer with id {customerId}
    Customer deleteCustomer(UUID customerId);
}
