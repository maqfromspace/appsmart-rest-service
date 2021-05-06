package com.maqfromspace.appsmartrestservice.repositories;

import com.maqfromspace.appsmartrestservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String name);
}
