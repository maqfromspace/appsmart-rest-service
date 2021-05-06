package com.maqfromspace.appsmartrestservice.dto;

import lombok.Data;

/**
 * Authentication request body
 */
@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}

