package com.maqfromspace.appsmartrestservice.configuration;

import com.maqfromspace.appsmartrestservice.security.jwt.JwtConfigurer;
import com.maqfromspace.appsmartrestservice.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Component;

/**
 * Security configuration
 */
@Component
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String CUSTOMER_ENDPOINT = "/api/v1/customers/**";
    private static final String PRODUCT_ENDPOINT = "/api/v1/products/**";


    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                    .disable()
                .csrf()
                    .disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers(HttpMethod.DELETE, CUSTOMER_ENDPOINT, PRODUCT_ENDPOINT).hasAuthority("ADMIN_ROLE")
                        .antMatchers(HttpMethod.PUT, CUSTOMER_ENDPOINT, PRODUCT_ENDPOINT).hasAuthority("ADMIN_ROLE")
                    .anyRequest()
                        .permitAll()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new com.maqfromspace.appsmartrestservice.configuration.CustomAuthenticationEntryPoint())
                .and()
                    .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
