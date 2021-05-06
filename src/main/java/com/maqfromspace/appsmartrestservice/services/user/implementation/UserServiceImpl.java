package com.maqfromspace.appsmartrestservice.services.user.implementation;

import com.maqfromspace.appsmartrestservice.entities.User;
import com.maqfromspace.appsmartrestservice.repositories.UserRepository;
import com.maqfromspace.appsmartrestservice.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maqfromspace.appsmartrestservice.entities.User;
/**
 * Implementation of UserService for spring security
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
