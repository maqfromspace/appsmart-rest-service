package com.maqfromspace.appsmartrestservice.controllers;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/delete_user_role.sql", "/insert_role.sql", "/insert_user.sql", "/insert_user_role.sql"})
public class AuthenticationControllerTest {
    @Autowired
    MockMvc mockMvc;
    public static final String LOGIN_URL = "http://localhost:8080/api/v1/auth/login";

    private void getToken() throws Exception {
        String username = "admin";
        String password = "adminpassword";
        String response = mockMvc
                .perform(
                        post(LOGIN_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"username\":\"" + username + "\"," +
                                        "\"password\":\"" + password + "\"" +
                                        "}")
                ).andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty()).andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(response);
        jsonObject.get("token");
    }

    private void getTokenWithIncorrectPassword() throws Exception {
        String username = "admin";
        String password = "sqlinjectionfromfpmhelloworldgogo";
        mockMvc
                .perform(
                        post(LOGIN_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"username\":\"" + username + "\"," +
                                        "\"password\":\"" + password + "\"" +
                                        "}")
                )
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().string("Invalid username or password"));
    }

    private void getTokenWithIncorrectUsernameAndPassword() throws Exception {
        String username = "hacker1";
        String password = "sqlinjectionfromfpmhelloworldgogo";
        mockMvc
                .perform(
                        post(LOGIN_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"username\":\"" + username + "\"," +
                                        "\"password\":\"" + password + "\"" +
                                        "}")
                )
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().string("Invalid username or password"));
    }

    @Test
    public void testAuthenticationController_testGetTokenWithCorrectParameters() throws Exception {
        getToken();
    }

    @Test
    public void testAuthenticationController_testGetTokenWithIncorrectPassword() throws Exception {
        getTokenWithIncorrectPassword();
    }

    @Test
    public void testAuthenticationController_testGetTokenWithIncorrectUsername() throws Exception {
        getTokenWithIncorrectUsernameAndPassword();
    }
}
