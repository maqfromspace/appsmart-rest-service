package com.maqfromspace.appsmartrestservice.services.product;

import com.maqfromspace.appsmartrestservice.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {
    Product getProduct(UUID productId);
    Product deleteProduct(UUID productId);
    Product editProduct(UUID productId, Product product);
    Product createProduct(UUID customerId, Product product);
    Page<Product> getCustomersProduct(UUID customerId, Pageable pageable);
}
