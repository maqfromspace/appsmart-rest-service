package com.maqfromspace.appsmartrestservice.services.product;

import com.maqfromspace.appsmartrestservice.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {
    //Get product with id {productId}
    Product getProduct(UUID productId);
    //Delete product with id {productId}
    Product deleteProduct(UUID productId);
    //Edit product with id {productId}
    Product editProduct(UUID productId, Product product);
    //Create new product for customer with id {customerId}
    Product createProduct(UUID customerId, Product product);
    //Get customer's {customerId} products page
    Page<Product> getCustomersProduct(UUID customerId, Pageable pageable);
}
