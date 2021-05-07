package com.maqfromspace.appsmartrestservice.app.security;

import com.maqfromspace.appsmartrestservice.security.jwt.JwtUser;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestJwtUser {
    @Test
    public void testGetId() {
        JwtUser jwtUser = new JwtUser(1L,"max", "leo", null);
        Assertions.assertEquals(1L,jwtUser.getId());
        Assertions.assertEquals("max",jwtUser.getUsername());
        Assertions.assertEquals("leo",jwtUser.getPassword());
    }
}
