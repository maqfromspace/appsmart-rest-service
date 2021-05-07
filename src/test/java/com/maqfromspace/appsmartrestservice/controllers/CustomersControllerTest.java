package com.maqfromspace.appsmartrestservice.controllers;

import com.jayway.jsonpath.JsonPath;
import com.maqfromspace.appsmartrestservice.repositories.CustomersRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/delete_user_role.sql","/insert_role.sql", "/insert_user.sql", "/insert_user_role.sql"})
public class CustomersControllerTest {

    public static final String CUSTOMERS_URL = "http://localhost:8080/api/v1/customers";
    public static final String LOGIN_URL = "http://localhost:8080/api/v1/auth/login";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CustomersRepository customersRepository;



    private String getToken() throws Exception {
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
        return "Bearer_" + jsonObject.get("token");
    }
    private void getCustomer(String uuid, String title) throws Exception {
        mockMvc
                .perform(
                        get(CUSTOMERS_URL + "/" + uuid)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.links").isNotEmpty());
    }
    private void getCustomers() throws Exception {
        mockMvc
                .perform(
                        get(CUSTOMERS_URL)
                )
                .andExpect(status().isOk());
    }
    private void getCustomerAfterEdit(String uuid, String title) throws Exception {
        mockMvc
                .perform(
                        get(CUSTOMERS_URL + "/" + uuid)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.links").isNotEmpty());
    }

    private void getCustomerAfterEditWithEmptyBody(String uuid, String title) throws Exception {
        mockMvc
                .perform(
                        get(CUSTOMERS_URL + "/" + uuid)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.links").isNotEmpty());
    }
    private void editCustomer(String uuid, String title) throws Exception {
        mockMvc
                .perform(
                        put(CUSTOMERS_URL + "/" + uuid)
                                .header(HttpHeaders.AUTHORIZATION, getToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"" + title + "\"}")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.links").isNotEmpty());
    }

    private void editCustomerWithEmptyBody(String uuid) throws Exception {
        mockMvc
                .perform(
                        put(CUSTOMERS_URL + "/" + uuid)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, getToken())
                                .content("{}")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.links").isNotEmpty());
    }

    private ResultActions addCustomer(String title) throws Exception {
        return mockMvc
                .perform(
                        post(CUSTOMERS_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"" + title +"\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.links").isNotEmpty());
    }
    private void addCustomerWithEmptyTitle() throws Exception {
        mockMvc
                .perform(
                        post(CUSTOMERS_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\": null}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Field title can't be null"));
    }

    private void deleteCustomer(UUID uuid, String title) throws Exception {
        System.out.println(uuid);
        mockMvc
                .perform(
                        delete(CUSTOMERS_URL + "/" + uuid)
                                .header(HttpHeaders.AUTHORIZATION, getToken())

                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.links").isNotEmpty());
    }

    private void deleteCustomerWithInvalidUuid(UUID uuid) throws Exception {
        System.out.println(uuid);
        mockMvc
                .perform(
                        delete(CUSTOMERS_URL + "/" + uuid)
                                .header(HttpHeaders.AUTHORIZATION, getToken())

                )
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Could not found customer with id " + uuid));
    }

    @Before
    public void beforeTest() {
        customersRepository.deleteAll();
    }

    @Test
    public void testCustomerController_addCustomer() throws Exception {
        addCustomer("Max");
    }

    @Test
    public void testCustomerController_getCustomer() throws Exception {
        ResultActions resultActions = addCustomer("Max");
        String id = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.id");
        getCustomer(id, "Max");
    }

    @Test
    public void testCustomerController_getCustomers() throws Exception {
        addCustomer("Max");
        addCustomer("Masha");
        getCustomers();
    }


    @Test
    public void testCustomerController_deleteCustomer() throws Exception {
        ResultActions resultActions = addCustomer("Max");
        String id = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.id");
        UUID uuid = UUID.fromString(id);
        deleteCustomer(uuid, "Max");
    }

    @Test
    public void testCustomerController_deleteCustomerWithInvalidUuid() throws Exception {
        deleteCustomerWithInvalidUuid(UUID.randomUUID());
    }

    @Test
    public void testCustomerController_getCustomerAfterEdit() throws Exception {
        ResultActions resultActions = addCustomer("Max");
        String id = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.id");
        getCustomer(id, "Max");
        editCustomer(id, "Misha");
        getCustomerAfterEdit(id, "Misha");
    }

    @Test
    public void testCustomerController_getCustomerAfterEditWithEmptyBody() throws Exception {
        ResultActions resultActions = addCustomer("Max");
        String id = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.id");
        getCustomer(id, "Max");
        editCustomerWithEmptyBody(id);
        getCustomerAfterEditWithEmptyBody(id, "Max");
    }

    @Test
    public void testCustomerController_editCustomerWithoutRequiredField() throws Exception {
        ResultActions resultActions = addCustomer("Max");
        String id = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.id");
        getCustomer(id, "Max");
        editCustomerWithEmptyBody(id);
        getCustomerAfterEditWithEmptyBody(id, "Max");
    }

    @Test
    public void testCustomerController_addCustomerWithNotValidArgumentInBody() throws Exception {
        addCustomerWithEmptyTitle();
    }
}
