package com.maqfromspace.appsmartrestservice.controllers;

import com.maqfromspace.appsmartrestservice.dto.authentication.AuthenticationRequestDto;
import com.maqfromspace.appsmartrestservice.dto.authentication.AuthenticationResponseDto;
import com.maqfromspace.appsmartrestservice.entities.User;
import com.maqfromspace.appsmartrestservice.security.jwt.JwtTokenProvider;
import com.maqfromspace.appsmartrestservice.services.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Authentication controller
 */
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth")
@Api(tags = {"Authentication"})
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @PostMapping("login")
    @ApiOperation(value = "Get token by username and password",
            notes = "Method allows get Bearer token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "Field 'username' can't be null"),
            @ApiResponse(code = 403, message = "Invalid username or password")})
    public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody AuthenticationRequestDto authenticationRequestDto) {
        try {
            String username = authenticationRequestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequestDto.getPassword()));
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
