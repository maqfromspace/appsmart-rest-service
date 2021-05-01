package com.maqfromspace.appsmartrestservice.repositories;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, UUID> {
}
