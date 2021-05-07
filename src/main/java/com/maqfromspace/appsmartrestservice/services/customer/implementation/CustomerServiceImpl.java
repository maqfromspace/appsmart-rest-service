package com.maqfromspace.appsmartrestservice.services.customer.implementation;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import com.maqfromspace.appsmartrestservice.exceptions.CustomerNotFoundException;
import com.maqfromspace.appsmartrestservice.repositories.CustomersRepository;
import com.maqfromspace.appsmartrestservice.services.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    final CustomersRepository customersRepository;

    public CustomerServiceImpl(@Autowired CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public Page<Customer> getCustomers(Pageable pageable) {
        Page<Customer> allByDeleteFlagIsFalse = customersRepository.findAllByDeleteFlagIsFalse(pageable);
        log.info("IN getCustomers -  {} customers successfully loaded", allByDeleteFlagIsFalse.getTotalElements());
        return allByDeleteFlagIsFalse;
    }

    @Override
    public Customer getCustomer(UUID customerId) {
        Customer customer = customersRepository.findByIdAndDeleteFlagIsFalse(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        log.info("IN getCustomer - customer with id: {} successfully loaded", customerId);
        return customer;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        Customer savedCustomer = customersRepository.save(customer);
        log.info("IN addCustomer - customer with id: {} successfully added", savedCustomer.getId());
        return savedCustomer;
    }

    @Override
    public Customer editCustomer(UUID customerId, Customer customer) {
        return customersRepository.findByIdAndDeleteFlagIsFalse(customerId)
                .map(currentCustomer -> {
                    if(customer.getTitle() != null) {
                        log.info("IN editCustomer - customer with id: {}  change title {} -> {}", customerId,currentCustomer.getTitle(), customer.getTitle());
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
                    customer.getProductList().forEach(product -> {
                            product.setDeleteFlag(true);
                            log.info("IN deleteCustomer - product with id: {} successfully deleted", product.getId());
                    });
                    log.info("IN deleteCustomer - customer with id: {} successfully deleted", customerId);
                    return customersRepository.save(customer);
                })
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}