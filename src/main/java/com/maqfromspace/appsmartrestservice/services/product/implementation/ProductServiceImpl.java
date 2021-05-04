package com.maqfromspace.appsmartrestservice.services.product.implementation;

import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.exceptions.CustomerNotFoundException;
import com.maqfromspace.appsmartrestservice.exceptions.ProductNotFoundException;
import com.maqfromspace.appsmartrestservice.repositories.CustomersRepository;
import com.maqfromspace.appsmartrestservice.repositories.ProductRepository;
import com.maqfromspace.appsmartrestservice.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        return productRepository.findByIdAndDeleteFlagIsFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public Product deleteProduct(UUID productId) {
        return productRepository.findByIdAndDeleteFlagIsFalse(productId)
                .map(x -> {
                    x.setDeleteFlag(true);
                    return productRepository.save(x);
                })
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public Product editProduct(UUID productId, Product product) {
        return productRepository.findByIdAndDeleteFlagIsFalse(productId)
                .map(editedProduct -> {
                    if(product.getTitle() != null)
                        editedProduct.setTitle(product.getTitle());
                    if(product.getPrice() != null)
                        editedProduct.setPrice(product.getPrice());
                    if(product.getDescription() != null)
                        editedProduct.setDescription(product.getDescription());
                    return productRepository.save(editedProduct);
                })
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public Product createProduct(UUID customerId, Product product) {
        return customersRepository.findByIdAndDeleteFlagIsFalse(customerId)
                .map(customer -> {
                    product.setCustomer(customer);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Override
    public Page<Product> getCustomersProduct(UUID customerId, Pageable pageable) {
        return productRepository.findByCustomerIdAndDeleteFlagIsFalse(customerId, pageable);
    }
}