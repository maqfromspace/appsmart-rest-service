package com.maqfromspace.appsmartrestservice.services.user;

import com.maqfromspace.appsmartrestservice.entities.User;
/**
 * User service for spring security
 */
public interface UserService {

    User findByUsername(String username);
}
