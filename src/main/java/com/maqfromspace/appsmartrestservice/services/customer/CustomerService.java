package com.maqfromspace.appsmartrestservice.services.customer;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CustomerService {
    Page<Customer> getCustomers(Pageable pageable);
    Customer getCustomer(UUID customerId);
    Customer addCustomer(Customer customer);
    Customer editCustomer(UUID customerId, Customer customer);
    Customer deleteCustomer(UUID customerId);
}
