package com.maqfromspace.appsmartrestservice.dto;

import lombok.Data;

/**
 * Authentication response body
 */
@Data
public class AuthenticationResponseDto {
    String username;
    String token;
}
