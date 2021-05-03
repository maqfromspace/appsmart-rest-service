package com.maqfromspace.appsmartrestservice.repositories;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByIdAndDeleteFlagIsFalse(UUID uuid);
    List<Customer> findAllByDeleteFlagIsFalse();
}
