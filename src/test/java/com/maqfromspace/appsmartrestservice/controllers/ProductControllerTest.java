package com.maqfromspace.appsmartrestservice.controllers;

import com.jayway.jsonpath.JsonPath;
import com.maqfromspace.appsmartrestservice.repositories.CustomersRepository;
import com.maqfromspace.appsmartrestservice.repositories.ProductRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    public static final String PRODUCT_URL = "http://localhost:8080/api/v1/products";
    public static final String CUSTOMERS_URL = "http://localhost:8080/api/v1/customers";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    CustomersRepository customersRepository;
    @Autowired
    ProductRepository productRepository;

    private ResultActions addCustomer(String title) throws Exception {
        return mockMvc
                .perform(
                        post(CUSTOMERS_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"title\":\"" + title + "\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links").isNotEmpty());
    }

    private ResultActions deleteCustomer(String uuid, String title) throws Exception {
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

    private ResultActions getProduct(String productId, String productTitle, Double productPrice, String productDescription) throws Exception {
        return mockMvc
                .perform(
                        get(PRODUCT_URL + "/" + productId)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(productTitle))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(productPrice))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(productDescription))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links").isNotEmpty());
    }
    private ResultActions getDeletedProduct(String productId) throws Exception {
        return mockMvc
                .perform(
                        get(PRODUCT_URL + "/" + productId)
                )
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Could not found product with id " + productId));
    }



    private ResultActions addProductToCustomer(String customerId, String productTitle, Double productPrice, String productDescription) throws Exception {
        return mockMvc
                .perform(
                        post(CUSTOMERS_URL + "/" + customerId + "/" + "products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"title\":\"" + productTitle + "\"," +
                                        "\"price\":" + productPrice + "," +
                                        "\"description\":\"" + productDescription + "\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(productTitle))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(productPrice))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(productDescription))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links").isNotEmpty());
    }

    private ResultActions getCustomersProduct(String customerId, String productId, String productTitle, Double productPrice, String productDescription) throws Exception {
        return mockMvc
                .perform(
                        get(CUSTOMERS_URL + "/" + customerId + "/" + "products")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())

                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.productList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.productList[0].id").value(productId));

    }

    private ResultActions getCustomersProductAfterDeleting(String customerId) throws Exception {
        return mockMvc
                .perform(
                        get(CUSTOMERS_URL + "/" + customerId + "/" + "products")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())

                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.productList").doesNotExist());

    }

    private ResultActions editProduct(String uuid, String productTitle, Double productPrice, String productDescription) throws Exception {
        return mockMvc
                .perform(
                        put(PRODUCT_URL + "/" + uuid)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"title\":\"" + productTitle + "\"," +
                                        "\"price\":" + productPrice + "," +
                                        "\"description\":\"" + productDescription + "\"}")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(uuid))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(productTitle))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(productPrice))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(productDescription))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links").isNotEmpty());
    }

    private ResultActions editProductTitle(String uuid, String productTitle) throws Exception {
        return mockMvc
                .perform(
                        put(PRODUCT_URL + "/" + uuid)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"title\":\"" + productTitle + "\"" +
                                        "}")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(uuid))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(productTitle));
    }

    private ResultActions editProductPrice(String uuid, Double productPrice) throws Exception {
        return mockMvc
                .perform(
                        put(PRODUCT_URL + "/" + uuid)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"price\":" + productPrice +
                                        "}")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(uuid))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(productPrice));
    }

    private ResultActions editProductDescription(String uuid, String description) throws Exception {
        return mockMvc
                .perform(
                        put(PRODUCT_URL + "/" + uuid)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"description\":\"" + description + "\"" +
                                        "}")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(uuid))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(description));
    }

    private ResultActions deleteProduct(String uuid, String productTitle, Double productPrice, String productDescription) throws Exception {
        System.out.println(uuid);
        return mockMvc
                .perform(
                        delete(PRODUCT_URL + "/" + uuid)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(uuid))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(productTitle))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(productPrice))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(productDescription))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.modifiedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links").isNotEmpty());
    }

    private ResultActions deleteNonexistentProduct(String uuid) throws Exception {
        System.out.println(uuid);
        return mockMvc
                .perform(
                        delete(PRODUCT_URL + "/" + uuid)
                )
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Could not found product with id " + uuid));

    }

    @Before
    public void beforeTest() {
        customersRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testProductController_addProduct() throws Exception {
        ResultActions resultActions = addCustomer("Max");
        String id = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "$.id");
        addProductToCustomer(id, "Snikers", 10.5, "Chocolate!");
    }

    @Test
    public void testProductController_editAllProductsField() throws Exception {
        ResultActions resultActionsAddCustomer = addCustomer("Max");

        String customerId = JsonPath.read(resultActionsAddCustomer.andReturn().getResponse().getContentAsString(), "$.id");
        ResultActions resultActionsAddProduct = addProductToCustomer(customerId, "Snikers", 10.5, "Chocolate!");

        String productId = JsonPath.read(resultActionsAddProduct.andReturn().getResponse().getContentAsString(), "$.id");
        editProduct(productId, "Mars", 11.5, "Chocolate with nuts!");
        editProductTitle(productId, "Bounty");
        editProductPrice(productId, 99.99);

        editProductDescription(productId, "Chocolate with coconuts!");

    }

    @Test
    public void testProductController_getProduct() throws Exception {
        ResultActions resultActionsAddCustomer = addCustomer("Max");
        String customerId = JsonPath.read(resultActionsAddCustomer.andReturn().getResponse().getContentAsString(), "$.id");
        ResultActions resultActionsAddProduct = addProductToCustomer(customerId, "Snikers", 10.5, "Chocolate!");

        String productId = JsonPath.read(resultActionsAddProduct.andReturn().getResponse().getContentAsString(), "$.id");
        getProduct(productId, "Snikers", 10.5, "Chocolate!");
    }

    @Test
    public void testProductController_deleteProduct() throws Exception {
        ResultActions resultActionsAddCustomer = addCustomer("Max");
        String customerId = JsonPath.read(resultActionsAddCustomer.andReturn().getResponse().getContentAsString(), "$.id");
        ResultActions resultActionsAddProduct = addProductToCustomer(customerId, "Snikers", 10.5, "Chocolate!");

        String productId = JsonPath.read(resultActionsAddProduct.andReturn().getResponse().getContentAsString(), "$.id");
        getProduct(productId, "Snikers", 10.5, "Chocolate!");

        deleteProduct(productId, "Snikers", 10.5, "Chocolate!");
        deleteNonexistentProduct(productId);
    }

    @Test
    public void testProductController_deleteCustomersProduct() throws Exception {
        ResultActions resultActionsAddCustomer = addCustomer("Max");
        String customerId = JsonPath.read(resultActionsAddCustomer.andReturn().getResponse().getContentAsString(), "$.id");
        ResultActions resultActionsAddFirstProduct = addProductToCustomer(customerId, "Snikers", 10.5, "Chocolate!");
        String firstProductId = JsonPath.read(resultActionsAddFirstProduct.andReturn().getResponse().getContentAsString(), "$.id");
        getProduct(firstProductId, "Snikers", 10.5, "Chocolate!");
        getCustomersProduct(customerId, firstProductId, "Snikers", 10.5, "Chocolate!");
        deleteProduct(firstProductId, "Snikers", 10.5, "Chocolate!");
        getCustomersProductAfterDeleting(customerId);
    }

    @Test
    public void testProductController_deleteCustomerWithProductAfterAndGetProductAfter() throws Exception {
        ResultActions resultActionsAddCustomer = addCustomer("Max");
        String customerId = JsonPath.read(resultActionsAddCustomer.andReturn().getResponse().getContentAsString(), "$.id");
        ResultActions resultActionsAddFirstProduct = addProductToCustomer(customerId, "Snikers", 10.5, "Chocolate!");
        String productId = JsonPath.read(resultActionsAddFirstProduct.andReturn().getResponse().getContentAsString(), "$.id");
        getProduct(productId, "Snikers", 10.5, "Chocolate!");
        getCustomersProduct(customerId, productId, "Snikers", 10.5, "Chocolate!");

        deleteCustomer(customerId, "Max");
        getDeletedProduct(productId);
    }
}