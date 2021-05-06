package com.maqfromspace.appsmartrestservice.app.utils;

import com.maqfromspace.appsmartrestservice.entities.Customer;
import com.maqfromspace.appsmartrestservice.entities.Product;
import com.maqfromspace.appsmartrestservice.utils.ProductAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.Links;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Testing ProductAssembler
 */
class ProductAssemblerTest {
    ProductAssembler productAssembler;
    Customer customer;
    Product product;
    UUID productId = UUID.randomUUID();
    UUID customerId = UUID.randomUUID();
    LocalDateTime now = LocalDateTime.now();
    String title = "Test product";
    String description = "Test description";
    Double price = 10.50;

    @BeforeEach
    void init() {
        productAssembler = new ProductAssembler();
        product = new Product();
        customer = new Customer();
        customer.setId(customerId);

        product = new Product();
        product.setId(productId);
        product.setTitle(title);
        product.setDescription(description);
        product.setCreatedAt(now);
        product.setModifiedAt(now);
        product.setDeleteFlag(false);
        product.setPrice(price);
        product.setCustomer(customer);
    }

    @Test
    void testProductAssembler_testProductConverting() {

        EntityModel<Product> productEntityModel = productAssembler.toModel(product);
        Product content = productEntityModel.getContent();

        assertThat(productId).isEqualTo(content.getId());
        assertThat(content.getCustomer().getId()).isEqualTo(customerId);
        assertThat(title).isEqualTo(content.getTitle());
        assertThat(description).isEqualTo(content.getDescription());
        assertThat(now).isEqualTo(content.getCreatedAt());
        assertThat(now).isEqualTo(content.getModifiedAt());
        assertThat(price).isEqualTo(content.getPrice());
        assertThat(content.isDeleteFlag()).isFalse();
    }

    @Test
    void testProductAssembler_testLink() {
        EntityModel<Product> productEntityModel = productAssembler.toModel(product);

        Links links = productEntityModel.getLinks();
        Link selfLink = links.getLink(LinkRelation.of("self")).get();
        Link customerLink = links.getLink(LinkRelation.of("customer")).get();
        Link allCustomerProductsLink = links.getLink(LinkRelation.of("allCustomerProducts")).get();
        Link allCustomerLink = links.getLink(LinkRelation.of("allCustomers")).get();

        assertThat(selfLink.getHref()).isEqualTo("/api/v1/products/" + product.getId());
        assertThat(customerLink.getHref()).isEqualTo("/api/v1/customers/" + customer.getId() );
        assertThat(allCustomerProductsLink.getHref()).isEqualTo("/api/v1/customers/" + customer.getId() + "/products");
        assertThat(allCustomerLink.getHref()).isEqualTo("/api/v1/customers");
    }
}
