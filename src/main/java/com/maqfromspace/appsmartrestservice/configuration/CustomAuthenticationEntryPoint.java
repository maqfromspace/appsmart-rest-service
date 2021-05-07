package com.maqfromspace.appsmartrestservice.configuration;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implementation of AuthenticationEntryPoint for changing spring security response
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
        String message = authException.getMessage();
        if(authException instanceof InsufficientAuthenticationException)
            message = "JWT token isn't valid or expired";
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);
        res.getWriter().write(message);
    }
}