package com.maqfromspace.appsmartrestservice.controllers;

import com.jayway.jsonpath.JsonPath;
import com.maqfromspace.appsmartrestservice.repositories.CustomersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class CustomersControllerTest {

    public static final String CUSTOMERS_URL = "http://localhost:8080/api/v1/customers";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CustomersRepository customersRepository;


    private ResultActions getCustomer(String uuid, String title) throws Exception {
        return mockMvc
                .perform(
                        get(CUSTOMERS_URL + "/" + uuid)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links").isNotEmpty());
    }
    private ResultActions getCustomers() throws Exception {
        return mockMvc
                .perform(
                        get(CUSTOMERS_URL)
                )
                .andExpect(status().isOk());
    }
    private ResultActions getCustomerAfterEdit(String uuid, String title) throws Exception {
        return mockMvc
                .perform(
                        get(CUSTOMERS_URL + "/" + uuid)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links").isNotEmpty());
    }

    private ResultActions getCustomerAfterEditWithEmptyBody(String uuid, String title) throws Exception {
        return mockMvc
                .perform(
                        get(CUSTOMERS_URL + "/" + uuid)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links").isNotEmpty());
    }
    private ResultActions editCustomer(String uuid, String title) throws Exception {
        return mockMvc
                .perform(
                        put(CUSTOMERS_URL + "/" + uuid)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"" + title +"\"}")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links").isNotEmpty());
    }

    private ResultActions editCustomerWithEmptyBody(String uuid) throws Exception {
        return mockMvc
                .perform(
                        put(CUSTOMERS_URL + "/" + uuid)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links").isNotEmpty());
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
                .andExpect(MockMvcResultMatchers.jsonPath("$._links").isNotEmpty());
    }
    private ResultActions addCustomerWithEmptyTitle() throws Exception {
        return mockMvc
                .perform(
                        post(CUSTOMERS_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\": null}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Field title can't be null"));
    }

    private ResultActions deleteCustomer(UUID uuid, String title) throws Exception {
        System.out.println(uuid);
        return mockMvc
                .perform(
                        delete(CUSTOMERS_URL + "/" + uuid)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links").isNotEmpty());
    }

    private ResultActions deleteCustomerWithInvalidUuid(UUID uuid) throws Exception {
        System.out.println(uuid);
        return mockMvc
                .perform(
                        delete(CUSTOMERS_URL + "/" + uuid)
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
