package com.maqfromspace.appsmartrestservice.services.product.implementation;

import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.exceptions.CustomerNotFoundException;
import com.maqfromspace.appsmartrestservice.exceptions.ProductNotFoundException;
import com.maqfromspace.appsmartrestservice.repositories.CustomersRepository;
import com.maqfromspace.appsmartrestservice.repositories.ProductRepository;
import com.maqfromspace.appsmartrestservice.services.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    final ProductRepository productRepository;
    final CustomersRepository customersRepository;

    public ProductServiceImpl(@Autowired ProductRepository productRepository,
                              @Autowired CustomersRepository customersRepository) {
        this.productRepository = productRepository;
        this.customersRepository = customersRepository;
    }

    @Override
    public Product getProduct(UUID productId) {
        Product product = productRepository.findByIdAndDeleteFlagIsFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        log.info("IN getProduct - product with id: {} successfully loaded", productId);
        return product;
    }

    @Override
    public Product deleteProduct(UUID productId) {
        Product product = productRepository.findByIdAndDeleteFlagIsFalse(productId)
                .map(x -> {
                    x.setDeleteFlag(true);
                    return productRepository.save(x);
                })
                .orElseThrow(() -> new ProductNotFoundException(productId));
        log.info("IN deleteProduct - product with id: {} successfully deleted", productId);
        return product;
    }

    @Override
    public Product editProduct(UUID productId, Product product) {
        return productRepository.findByIdAndDeleteFlagIsFalse(productId)
                .map(editedProduct -> {
                    if(product.getTitle() != null) {
                        log.info("IN editProduct - product with id: {}  change title {} -> {}", productId,editedProduct.getTitle(), product.getTitle());
                        editedProduct.setTitle(product.getTitle());
                    }
                    if(product.getPrice() != null) {
                        log.info("IN editProduct - product with id: {}  change price {} -> {}", productId,editedProduct.getPrice(), product.getPrice());
                        editedProduct.setPrice(product.getPrice());
                    }
                    if(product.getDescription() != null) {
                        log.info("IN editProduct - product with id: {}  change description {} -> {}", productId,editedProduct.getDescription(), product.getDescription());
                        editedProduct.setDescription(product.getDescription());
                    }
                    return productRepository.save(editedProduct);
                })
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public Product createProduct(UUID customerId, Product product) {
        Product product1 = customersRepository.findByIdAndDeleteFlagIsFalse(customerId)
                .map(customer -> {
                    product.setCustomer(customer);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        log.info("IN createProduct - product with id: {} successfully created for customer with id: {}", product1.getId(), customerId);
        return product1;
    }

    @Override
    public Page<Product> getCustomersProduct(UUID customerId, Pageable pageable) {
        Page<Product> byCustomerIdAndDeleteFlagIsFalse = productRepository.findByCustomerIdAndDeleteFlagIsFalse(customerId, pageable);
        log.info("IN getCustomersProduct -  {} products successfully loaded for customer with id: {}", byCustomerIdAndDeleteFlagIsFalse.getTotalElements(), customerId);
        return byCustomerIdAndDeleteFlagIsFalse;
    }
}