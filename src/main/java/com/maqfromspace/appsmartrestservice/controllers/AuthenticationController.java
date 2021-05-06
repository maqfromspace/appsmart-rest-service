package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.dto.AuthenticationRequestDto;
import com.maqfromspace.appsmartrestservice.dto.AuthenticationResponseDto;
import com.maqfromspace.appsmartrestservice.entities.User;
import com.maqfromspace.appsmartrestservice.security.jwt.JwtTokenProvider;
import com.maqfromspace.appsmartrestservice.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication controller
 */
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
            authenticationResponseDto.setUsername(username);
            authenticationResponseDto.setToken(token);
            return ResponseEntity.ok(authenticationResponseDto);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
