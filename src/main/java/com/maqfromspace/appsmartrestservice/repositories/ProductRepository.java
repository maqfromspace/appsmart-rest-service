package com.maqfromspace.appsmartrestservice.repositories;

import com.maqfromspace.appsmartrestservice.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByIdAndDeleteFlagIsFalse(UUID productId);
    Page<Product> findByCustomerIdAndDeleteFlagIsFalse(UUID customerId, Pageable pageable);
}
