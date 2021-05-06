package com.maqfromspace.appsmartrestservice.app.utils;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.utils.CustomerAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.Links;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


/**
 * Testing CustomerAssembler
 */
class CustomerAssemblerTest {
    CustomerAssembler customerAssembler;
    Customer customer;
    Product product;
    UUID uuid = UUID.randomUUID();
    LocalDateTime now = LocalDateTime.now();
    String title = "Test product";

    @BeforeEach
    void init() {
        customerAssembler = new CustomerAssembler();

        product = new Product();
        product.setId(UUID.randomUUID());

        customer = new Customer();
        customer.setId(uuid);
        customer.setTitle(title);
        customer.setDeleteFlag(false);
        customer.setCreatedAt(now);
        customer.setModifiedAt(now);
        customer.setProductList(Collections.singletonList(product));


    }

    @Test
    void testCustomerAssembler_testCustomerConverting() {

        EntityModel<Customer> customerEntityModel = customerAssembler.toModel(customer);
        Customer content = customerEntityModel.getContent();

        assertThat(uuid).isEqualTo(content.getId());
        assertThat(now).isEqualTo(content.getCreatedAt());
        assertThat(now).isEqualTo(content.getModifiedAt());
        assertThat(title).isEqualTo(customer.getTitle());
        assertThat(product).isEqualTo(customer.getProductList().get(0));
        assertThat(content.isDeleteFlag()).isFalse();
    }

    @Test
    void testCustomerAssembler_testLink() {
        EntityModel<Customer> customerEntityModel = customerAssembler.toModel(customer);

        Links links = customerEntityModel.getLinks();
        Link selfLink = links.getLink(LinkRelation.of("self")).get();
        Link productsLink = links.getLink(LinkRelation.of("products")).get();
        Link customerLink = links.getLink(LinkRelation.of("customers")).get();


        assertThat(selfLink.getHref()).isEqualTo("/api/v1/customers/" + customer.getId());
        assertThat(productsLink.getHref()).isEqualTo("/api/v1/customers/" + customer.getId() + "/products");
        assertThat(customerLink.getHref()).isEqualTo("/api/v1/customers");
    }
}
