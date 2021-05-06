package com.maqfromspace.appsmartrestservice.security.jwt;

import com.maqfromspace.appsmartrestservice.entities.Role;
import com.maqfromspace.appsmartrestservice.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Helper that convert User -> JwtUser and  List<Roles> -> List<GrantedAuthority>
 */
public final class JwtUserFactory {
    private JwtUserFactory(){}
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
