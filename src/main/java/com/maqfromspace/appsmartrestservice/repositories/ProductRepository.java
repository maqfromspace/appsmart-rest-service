package com.maqfromspace.appsmartrestservice.repositories;

import com.maqfromspace.appsmartrestservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByIdAndDeleteFlagIsFalse(UUID productId);
    List<Product> findByCustomerIdAndDeleteFlagIsFalse(UUID customerId);
}
