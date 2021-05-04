package com.maqfromspace.appsmartrestservice.services.customer.implementation;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import com.maqfromspace.appsmartrestservice.exceptions.CustomerNotFoundException;
import com.maqfromspace.appsmartrestservice.repositories.CustomersRepository;
import com.maqfromspace.appsmartrestservice.services.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    final CustomersRepository customersRepository;

    public CustomerServiceImpl(@Autowired CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public Page<Customer> getCustomers(Pageable pageable) {
        return customersRepository.findAllByDeleteFlagIsFalse(pageable);
    }

    @Override
    public Customer getCustomer(UUID customerId) {
        return customersRepository.findByIdAndDeleteFlagIsFalse(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customersRepository.save(customer);
    }

    @Override
    public Customer editCustomer(UUID customerId, Customer customer) {
        return customersRepository.findByIdAndDeleteFlagIsFalse(customerId)
                .map(currentCustomer -> {
                    if(customer.getTitle() != null) {
                        currentCustomer.setTitle(customer.getTitle());
                        return customersRepository.save(currentCustomer);
                    }
                    else
                        return currentCustomer;
                })
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Override
    public Customer deleteCustomer(UUID customerId) {
        return customersRepository.findByIdAndDeleteFlagIsFalse(customerId)
                .map(customer -> {
                    customer.setDeleteFlag(true);
                    customer.getProductList().forEach(product ->
                            product.setDeleteFlag(true));
                    return customersRepository.save(customer);
                })
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}