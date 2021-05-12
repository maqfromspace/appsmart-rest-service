package com.maqfromspace.appsmartrestservice.services.user.implementation;

import com.maqfromspace.appsmartrestservice.entities.User;
import com.maqfromspace.appsmartrestservice.repositories.UserRepository;
import com.maqfromspace.appsmartrestservice.services.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of UserService for spring security
 */
@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
